package biz.ohrae.challenge_screen.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import biz.ohrae.challenge.ui.components.header.Header
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge.ui.theme.DefaultBackground
import biz.ohrae.challenge_repo.util.PermissionUtils
import biz.ohrae.challenge_screen.BuildConfig
import biz.ohrae.challenge_screen.ui.BaseActivity
import biz.ohrae.challenge_screen.ui.detail.ChallengeDetailActivity
import biz.ohrae.challenge_screen.ui.dialog.*
import biz.ohrae.challenge_screen.ui.login.LoginActivity
import biz.ohrae.challenge_screen.ui.mychallenge.MyChallengeActivity
import biz.ohrae.challenge_screen.ui.mychallenge.MyChallengeViewModel
import biz.ohrae.challenge_screen.ui.niceid.NiceIdActivity
import biz.ohrae.challenge_screen.ui.profile.ChallengeProfileActivity
import biz.ohrae.challenge_screen.ui.register.RegisterActivity
import biz.ohrae.challenge_screen.ui.terms.TermsWebViewActivity
import biz.ohrae.challenge_screen.ui.welcome.WelcomeActivity
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    companion object {
        const val MAIN_USER_FILTER: String = "on_going"
    }
    private lateinit var viewModel: ChallengeMainViewModel
    private lateinit var myChallengeViewModel: MyChallengeViewModel
    private lateinit var launcher: ActivityResultLauncher<Intent>

    private lateinit var mainClickListener: MainClickListener
    private var periodTypeValue: String = ""
    private var perWeekValue: String = ""
    private var periodValue: String = ""
    private var adultOnlyValue: String = ""
    private var paymentType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeMainViewModel::class.java]
        myChallengeViewModel = ViewModelProvider(this)[MyChallengeViewModel::class.java]

        setContent {
            ChallengeInTheme {
                val isLoading by viewModel.isLoading.observeAsState(false)
                if (isLoading) {
                    Dialog(onDismissRequest = { /*TODO*/ }) {
                        LoadingDialog()
                    }
                }
                BuildContent()
            }
        }

        initClickListeners()
        observeViewModels()
        initLauncher()
        if (intent != null && intent.data != null) {
            handleDeepLink(intent.data)
        }

        viewModel.selectFilter("all")
    }

    override fun onResume() {
        super.onResume()

        viewModel.userData()
        viewModel.tokenCheck()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.e("intent data : ${intent?.data.toString()}")
        val uri = intent?.data
        if (uri != null) {
            handleDeepLink(uri)
        }

        val challengeId = intent?.getStringExtra("challengeId")
        if (challengeId != null) {
            goDetail(challengeId, true)
        }
    }

    @Composable
    private fun BuildContent() {
        val mainScreenState by viewModel.mainScreenState.observeAsState()
        val filterState by viewModel.filterState.observeAsState()
        val state by viewModel.userChallengeListState.observeAsState()
        val isRefreshing by viewModel.isRefreshing.observeAsState(false)
        val userData by viewModel.userData.observeAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DefaultBackground)
        ) {
            Header(
                profileImage = userData?.imageFile?.path,
                goMyChallenge = { goMyChallenge() },
            )
            if (mainScreenState != null) {
                ChallengeMainScreen(
                    mainScreenState = mainScreenState,
                    filterState = filterState!!,
                    clickListener = mainClickListener,
                    userChallengeListState = state,
                    isRefreshing = isRefreshing,
                    onBottomReached = {
                        onBottomReached()
                    },
                    onRefresh = {
                        viewModel.isRefreshing(true)
                        refresh()
                    }
                )
            }
        }
    }

    private fun init() {
        myChallengeViewModel.getUserData()
        viewModel.getChallengeList("", "", "", "", "", "", isInit = true)
        viewModel.getUserChallengeList(MAIN_USER_FILTER, isInit = true)
        viewModel.selectPeriodType("")
        viewModel.selectPeriod("")
        viewModel.selectIsAdultOnly("")
        viewModel.initTokenValid()

        if (prefs.getIsFirstLaunch()) {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun refresh() {
        myChallengeViewModel.getUserData()
        viewModel.getChallengeList(paymentType, periodTypeValue, perWeekValue, periodValue, "", adultOnlyValue, isInit = true)
        viewModel.getUserChallengeList(MAIN_USER_FILTER, isInit = true)
    }

    override fun initClickListeners() {
        mainClickListener = object : MainClickListener {
            override fun onClickPurchaseTicket() {
                TODO("Not yet implemented")
            }

            override fun onClickRegister() {
                val user = prefs.getUserData()
                user?.let {
                    if (it.is_identified <= 0 && it.phone_number != "01000000000") {
                        val intent = Intent(this@MainActivity, NiceIdActivity::class.java)
                        intent.putExtra("userId", it.id)
                        startActivity(intent)
                        return
                    }
                }

                if (BuildConfig.DEBUG) {
                    val intent = Intent(this@MainActivity, RegisterActivity::class.java)
                    launcher.launch(intent)
                } else {
                    showCreateDialog()
                }
            }

            override fun onClickChallengeItem(id: String) {
                goDetail(id)
            }

            override fun onClickFilterType(filterType: String) {
                Timber.e("filterType : $filterType")

                if (!isClickable()) {
                    return
                }

                if (filterType == "filter") {
                    val dialog = FilterDialog(viewModel)
                    dialog.setListener(object : FilterDialogListener {
                        override fun clickPositive(
                            periodType: String,
                            perWeek: String,
                            period: String,
                            isAdultOnly: String
                        ) {
                            val selectedFilterType = viewModel.filterState.value?.selectFilterType
                            paymentType = selectedFilterType.toString()
                            periodTypeValue = periodType
                            perWeekValue = perWeek
                            periodValue = period
                            adultOnlyValue = isAdultOnly
                            viewModel.isLoading(true)
                            viewModel.getChallengeList(
                                selectedFilterType.toString(),
                                periodType,
                                perWeek,
                                period,
                                "",
                                isAdultOnly,
                                isInit = true
                            )
                            dialog.dismiss()
                        }

                        override fun clickNegative() {
                            dialog.dismiss()
                        }

                        override fun clickInitialization() {
                            dialog.dismiss()
                        }

                        override fun selectVerificationPeriodType(item: String) {
                            viewModel.selectPeriodType(item)
                        }

                        override fun selectPeriod(item: String) {
                            viewModel.selectPeriod(item)
                        }

                        override fun selectIsAdultOnly(item: String) {
                            viewModel.selectIsAdultOnly(item)
                        }
                    })
                    dialog.isCancelable = false
                    dialog.show(supportFragmentManager, "FilterDialog")
                } else {
                    paymentType = filterType
                    viewModel.isLoading(true)
                    viewModel.selectFilter(filterType)
                    viewModel.selectPeriodType(periodTypeValue)
                    viewModel.selectPeriod(periodValue)
                    viewModel.selectIsAdultOnly(adultOnlyValue)
                    viewModel.getChallengeList(paymentType = paymentType, periodTypeValue, perWeekValue, periodValue, "", adultOnlyValue, isInit = true)
                }
            }

            override fun onClickChallengeAuthItem(id: String, type: Int) {
                val permissions = PermissionUtils.getPermissions()

                val permissionResults = mutableListOf<String>()
                permissions.forEach {
                    val result = ContextCompat.checkSelfPermission(this@MainActivity, it)
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionResults.add(it)
                    }
                }

                if (permissionResults.isNotEmpty()) {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        permissionResults.toTypedArray(),
                        100
                    )
                } else {
                    goDetail(id, isPhoto = type)
                }
            }

            override fun onClickTopBanner() {
                val intent = Intent(this@MainActivity, BannerDetailActivity::class.java)
                startActivity(intent)
            }

            override fun onClickInitialization() {
                viewModel.selectFilter("all")
                viewModel.selectPeriodType("")
                viewModel.selectPeriod("")
                viewModel.selectIsAdultOnly("")
                paymentType = "all"
                periodTypeValue = ""
                periodValue = ""
                adultOnlyValue = ""
                perWeekValue = ""
                viewModel.getChallengeList(paymentType = paymentType, periodTypeValue, perWeekValue, periodValue, "", adultOnlyValue, isInit = true)
            }

            override fun onClickMyChallengeCard(id: String) {
                goDetail(id)
            }
        }
    }

    override fun observeViewModels() {
        viewModel.tokenValid.observe(this) {
            it?.let {
                Timber.e("check tokenValid")
                if (!it) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    if (viewModel.challengeListPage.value == 1) {
                        init()
                    } else {
                        viewModel.getUserChallengeList(MAIN_USER_FILTER, isInit = true)
                    }
                }
            }
        }

        viewModel.errorData.observe(this) {
            if (it != null) {
                showSnackBar(it.code, it.message)
                viewModel.initErrorData()
            }
        }

        myChallengeViewModel.isNicknameValid.observe(this) {
            if (it != null && it != 1) {
                if (!prefs.getIsFirstLaunch()) {
                    goProfileActivity()
                }
            }
        }
    }

    private fun initLauncher() {
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val challengeId = it?.data?.getStringExtra("challengeId")
                if (challengeId != null) {
                    goDetail(challengeId, true)
                }
            }
        }
    }

    private fun onBottomReached() {
        viewModel.getChallengeList(paymentType, periodTypeValue, perWeekValue, periodValue, "", adultOnlyValue, isInit = false)
    }

    private fun goDetail(id: String, isParticipant: Boolean = false, isPhoto: Int = 0) {
        val intent = Intent(this, ChallengeDetailActivity::class.java)
        intent.putExtra("challengeId", id)
        intent.putExtra("isParticipant", isParticipant)
        intent.putExtra("isPhoto", isPhoto)
        startActivity(intent)
    }

    private fun goMyChallenge() {
        val intent = Intent(this, MyChallengeActivity::class.java)
        startActivity(intent)
    }

    private fun showCreateDialog() {
        if (!isClickable()) {
            return
        }

        val dialog = ChallengeCreateDialog()
        dialog.isCancelable = true
        dialog.setListener(object : CustomDialogListener {
            override fun clickPositive() {
                dialog.dismiss()
                val intent = Intent(this@MainActivity, RegisterActivity::class.java)
                launcher.launch(intent)
            }

            override fun clickNegative() {
                dialog.dismiss()
                val intent = Intent(this@MainActivity, TermsWebViewActivity::class.java)
                intent.putExtra("type", "caution")
                startActivity(intent)
            }
        })

        dialog.show(supportFragmentManager, "ChallengeCreateDialog")
    }

    private fun handleDeepLink(uri: Uri? = null) {
        if (uri != null) {
            val challengeId = uri.getQueryParameter("id")
            if (!challengeId.isNullOrEmpty()) {
                goDetail(challengeId)
            }
        } else {
            Firebase.dynamicLinks
                .getDynamicLink(intent)
                .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                    // Get deep link from result (may be null if no link is found)
                    var deepLink: Uri? = null
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.link
                    }

                    // Handle the deep link. For example, open the linked
                    // content, or apply promotional credit to the user's
                    // account.
                    // ...
                    Timber.e("deepLink : $deepLink")
                    val challengeId = deepLink?.getQueryParameter("id")
                    if (!challengeId.isNullOrEmpty()) {
                        goDetail(challengeId)
                    }
                }
                .addOnFailureListener(this) { e -> Timber.w("getDynamicLink:onFailure", e) }
        }
    }

    private fun goProfileActivity() {
        val intent = Intent(this, ChallengeProfileActivity::class.java)
        intent.putExtra("isInit", true)
        startActivity(intent)
    }
}

sealed class ChallengeNavScreen(val route: String) {
    object ChallengeMain : ChallengeNavScreen("ChallengeMain")
}