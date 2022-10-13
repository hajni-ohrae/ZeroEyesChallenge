package biz.ohrae.challenge_screen.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import biz.ohrae.challenge.ui.components.header.Header
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge_screen.ui.detail.ChallengeDetailActivity
import biz.ohrae.challenge_screen.ui.detail.ChallengeJoinedDetailActivity
import biz.ohrae.challenge_screen.ui.dialog.FilterDialog
import biz.ohrae.challenge_screen.ui.dialog.FilterDialogListener
import biz.ohrae.challenge_screen.ui.login.LoginActivity
import biz.ohrae.challenge_screen.ui.mychallenge.MyChallengeActivity
import biz.ohrae.challenge_screen.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ChallengeMainViewModel
    private lateinit var mainClickListener: MainClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeMainViewModel::class.java]

        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }

        initClickListener()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    @Composable
    private fun BuildContent() {
        val mainScreenState by viewModel.mainScreenState.observeAsState()
        val filterState by viewModel.filterState.observeAsState()
        val state by viewModel.userChallengeListState.observeAsState()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Header(goMyChallenge = { goMyChallenge() })
            if (mainScreenState != null) {
                ChallengeMainScreen(
                    mainScreenState = mainScreenState,
                    filterState = filterState!!,
                    clickListener = mainClickListener,
                    userChallengeListState = state
                )
            }
        }
    }

    private fun init() {
        viewModel.getChallengeList("","","","","","")
        viewModel.getUserChallengeList()
        viewModel.selectPeriodType("")
        viewModel.selectPeriod("")
        viewModel.selectIsAdultOnly("")
    }

    private fun initClickListener() {
        mainClickListener = object : MainClickListener {
            override fun onClickPurchaseTicket() {
                TODO("Not yet implemented")
            }

            override fun onClickRegister() {
                val intent = Intent(this@MainActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

            override fun onClickChallengeItem(id: String) {
                goDetail(id)
            }

            override fun onClickFilterType(filterType: String) {
                if (filterType == "filter") {
                    val dialog = FilterDialog(viewModel)
                    dialog.setListener(object : FilterDialogListener {
                        override fun clickPositive(
                            verificationPeriodType: String,
                            perWeek: String,
                            period: String,
                            isAdultOnly: String
                        ) {
                            val filterType = viewModel.filterState.value?.selectFilterType
                            viewModel.getChallengeList(filterType.toString(),verificationPeriodType, perWeek, period,"", isAdultOnly)
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
                    viewModel.selectFilter(filterType)
                    viewModel.getChallengeList(paymentType = filterType,"","","","","")
                }
            }

            override fun onClickChallengeAuthItem(id: String) {
                goDetail(id)
            }
        }
    }

    private fun observeViewModel() {
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
    }

    private fun goDetail(id: String) {
        val intent = Intent(this, ChallengeDetailActivity::class.java)
        intent.putExtra("challengeId", id)
        startActivity(intent)
    }
    private fun goChallengeJoinedDetail(id: String) {
        val intent = Intent(this, ChallengeJoinedDetailActivity::class.java)
        intent.putExtra("challengeId", id)
        startActivity(intent)
    }

    private fun goMyChallenge() {
        val intent = Intent(this, MyChallengeActivity::class.java)
        startActivity(intent)
    }
}

sealed class ChallengeNavScreen(val route: String) {
    object ChallengeMain : ChallengeNavScreen("ChallengeMain")
}