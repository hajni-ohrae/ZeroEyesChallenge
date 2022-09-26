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
import dagger.hilt.android.AndroidEntryPoint
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge_screen.ui.detail.ChallengeDetailActivity
import biz.ohrae.challenge_screen.ui.dialog.CustomDialogListener
import biz.ohrae.challenge_screen.ui.dialog.FilterDialog
import biz.ohrae.challenge_screen.ui.mychallenge.MyChallengeActivity
import biz.ohrae.challenge_screen.ui.register.RegisterActivity
import biz.ohrae.challenge_screen.ui.register.RegisterClickListener

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ChallengeMainViewModel
    private lateinit var mainClickListener: MainClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChallengeMainViewModel::class.java]

        init()
        initClickListener()
        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }
    }

    @Composable
    private fun BuildContent() {
        val mainScreenState by viewModel.mainScreenState.observeAsState()
        val filterState by viewModel.filterState.observeAsState()
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Header(goMyChallenge = { goMyChallenge() })
            if (mainScreenState != null) {
                ChallengeMainScreen(
                    mainScreenState = mainScreenState,
                    filterState = filterState!!,
                    clickListener = mainClickListener
                )
            }
        }
    }

    private fun init() {
        viewModel.getChallengeList()
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
                    val dialog = FilterDialog()
                    dialog.setListener(object : CustomDialogListener {
                        override fun clickPositive() {
                            dialog.dismiss()
                        }

                        override fun clickNegative() {
                            dialog.dismiss()
                        }
                    })
                    dialog.isCancelable = false
                    dialog.show(supportFragmentManager, "FilterDialog")

                } else {
                    viewModel.selectFilter(filterType)
                    viewModel.getChallengeList(filterType)
                }
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
    }

    private fun goDetail(id: String) {
        val intent = Intent(this, ChallengeDetailActivity::class.java)
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