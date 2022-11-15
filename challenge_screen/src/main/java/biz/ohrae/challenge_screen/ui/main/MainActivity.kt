package biz.ohrae.challenge_screen.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import biz.ohrae.challenge.ui.components.header.Header
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge.ui.theme.DefaultBackground
import biz.ohrae.challenge_screen.BuildConfig
import biz.ohrae.challenge_screen.ui.BaseActivity
import biz.ohrae.challenge_screen.ui.detail.ChallengeDetailActivity
import biz.ohrae.challenge_screen.ui.dialog.*
import biz.ohrae.challenge_screen.ui.login.LoginActivity
import biz.ohrae.challenge_screen.ui.mychallenge.MyChallengeActivity
import biz.ohrae.challenge_screen.ui.mychallenge.MyChallengeViewModel
import biz.ohrae.challenge_screen.ui.niceid.NiceIdActivity
import biz.ohrae.challenge_screen.ui.policy.PolicyActivity
import biz.ohrae.challenge_screen.ui.register.RegisterActivity
import biz.ohrae.challenge_screen.ui.welcome.WelcomeActivity
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var viewModel: ChallengeMainViewModel
    private lateinit var myChallengeViewModel: MyChallengeViewModel

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
        if (intent != null && intent.data != null) {
            handleDeepLink(intent.data)
        }
    }

    override fun onResume() {
        super.onResume()
        init()
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DefaultBackground)
        ) {
            Header(goMyChallenge = { goMyChallenge() })
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
                        init()
                    }
                )
            }
        }
    }

    private fun init() {
        myChallengeViewModel.getUserData()
        viewModel.getChallengeList()
        viewModel.getUserChallengeList("", isInit = true)
        viewModel.selectPeriodType("")
        viewModel.selectPeriod("")
        viewModel.selectIsAdultOnly("")

        if (prefs.getIsFirstLaunch()) {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun initClickListeners() {
        mainClickListener = object : MainClickListener {
            override fun onClickPurchaseTicket() {
                TODO("Not yet implemented")
            }

            override fun onClickRegister() {
                val user = prefs.getUserData()
                user?.let {
                    if (it.is_identified <= 0) {
                        val intent = Intent(this@MainActivity, NiceIdActivity::class.java)
                        intent.putExtra("userId", it.id)
                        startActivity(intent)
                        return
                    }
                }

                if (BuildConfig.DEBUG) {
                    val intent = Intent(this@MainActivity, RegisterActivity::class.java)
                    startActivity(intent)
                } else {
                    showCreateDialog()
                }
            }

            override fun onClickChallengeItem(id: String) {
                goDetail(id)
            }

            override fun onClickFilterType(filterType: String) {
                if (!isClickable()) {
                    return
                }

                if (filterType == "filter") {
                    val dialog = FilterDialog(viewModel)
                    dialog.setListener(object : FilterDialogListener {
                        override fun clickPositive(
                            verificationPeriodType: String,
                            perWeek: String,
                            period: String,
                            isAdultOnly: String
                        ) {
                            val selectedFilterType = viewModel.filterState.value?.selectFilterType
                            paymentType = selectedFilterType.toString()
                            periodTypeValue = verificationPeriodType
                            perWeekValue = perWeek
                            periodValue = period
                            adultOnlyValue = isAdultOnly
                            viewModel.isLoading(true)
                            viewModel.getChallengeList(
                                selectedFilterType.toString(),
                                verificationPeriodType,
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
                    viewModel.isLoading(true)
                    viewModel.selectFilter(filterType)
                    viewModel.getChallengeList(paymentType = filterType, isInit = true)
                }
            }

            override fun onClickChallengeAuthItem(id: String) {
                goDetail(id)
            }

            override fun onClickTopBanner() {
                val intent = Intent(this@MainActivity, BannerDetailActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun observeViewModels() {
//        viewModel.isChallengeCreate.observe(this) {
//            if (it) {
//                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
//                startActivity(intent)
//            }
//        }

        viewModel.tokenValid.observe(this) {
            if (!it) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        viewModel.errorData.observe(this) {
            if (it != null) {
                showSnackBar(it.code, it.message)
            }
        }
    }

    private fun onBottomReached() {
        viewModel.getChallengeList(
            paymentType,
            periodTypeValue,
            perWeekValue,
            periodValue,
            is_adult_only = adultOnlyValue
        )
    }

    private fun goDetail(id: String, isParticipant: Boolean = false) {
        val intent = Intent(this, ChallengeDetailActivity::class.java)
        intent.putExtra("challengeId", id)
        intent.putExtra("isParticipant", isParticipant)
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
                startActivity(intent)
            }

            override fun clickNegative() {
                dialog.dismiss()
                val intent = Intent(this@MainActivity, PolicyActivity::class.java)
                intent.putExtra("policyType", "Caution")
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
}

sealed class ChallengeNavScreen(val route: String) {
    object ChallengeMain : ChallengeNavScreen("ChallengeMain")
}