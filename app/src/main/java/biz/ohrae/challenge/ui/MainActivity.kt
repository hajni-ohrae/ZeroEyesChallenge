package biz.ohrae.challenge.ui

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.ohrae.challenge.ui.test.TestDropdownScreen
import biz.ohrae.challenge.ui.test.TestInputTextScreen
import biz.ohrae.challenge.ui.test.TestMainClickListener
import biz.ohrae.challenge.ui.test.TestMainScreen
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var testMainClickListener: TestMainClickListener
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        testMainClickListener = object : TestMainClickListener {
            override fun onClickTestDropDown() {
                navController.navigate(NavScreen.DropDownTest.route)
            }

            override fun onClickTestEditText() {
                navController.navigate(NavScreen.InputTextTest.route)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        } else {
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }
    }

    @Composable
    private fun BuildContent() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Navigation()
        }
    }

    @Composable
    private fun Navigation() {
        navController = rememberNavController()

        NavHost(navController = navController, startDestination = NavScreen.TestMain.route) {
            composable(NavScreen.TestMain.route) {
                TestMainScreen(clickListener = testMainClickListener)
            }
            composable(NavScreen.DropDownTest.route) {
                TestDropdownScreen()
            }
            composable(NavScreen.InputTextTest.route) {
                TestInputTextScreen()
            }
        }
    }


}

sealed class NavScreen(val route: String) {
    object TestMain : NavScreen("TestMain")
    object DropDownTest : NavScreen("DropDownTest")
    object InputTextTest : NavScreen("InputTextTest")
}