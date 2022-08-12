package biz.ohrae.challenge.ui.screen.welcome

import WelcomeScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import biz.ohrae.challenge.model.WelcomeScreenState
import biz.ohrae.challenge.ui.components.button.FlatButton
import biz.ohrae.challenge.ui.components.card.BottomSheetCard
import biz.ohrae.challenge.ui.theme.ChallengeInTheme
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {
    private lateinit var viewModel: WelcomeViewModel
    private lateinit var welcomeScreenClickListener: WelcomeScreenClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WelcomeViewModel::class.java]

        initClickListeners()

        setContent {
            ChallengeInTheme {
                BuildContent()
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun BuildContent() {
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed),
        )
        val scope = rememberCoroutineScope()
        scope.launch {
            bottomSheetScaffoldState.bottomSheetState.expand()
        }

        val state by viewModel.welcomeScreenState.observeAsState()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BottomSheetScaffold(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                scaffoldState = bottomSheetScaffoldState,
                sheetShape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp),
                sheetGesturesEnabled = false,
                sheetContent = {
                    BottomSheetContent(
                        modifier = Modifier.fillMaxWidth(),
                        state = state
                    )
                },
                sheetPeekHeight = (0).dp
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    if (state != null) {
                        WelcomeScreen(
                            state = state!!,
                            onPageChanged = {
                                viewModel.setPage(it)
                            }
                        )
                    }
                }
            }
        }
    }

    @Preview(
        widthDp = 360,
        showBackground = true
    )
    @Composable
    private fun BottomSheetContent(
        modifier: Modifier = Modifier,
        state: WelcomeScreenState? = WelcomeScreenState()
    ) {
        if (state != null) {
            Timber.e("state : ${Gson().toJson(state)}")
            BottomSheetCard(
                modifier = modifier
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp, 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(42.dp))
                    Text(
                        text = state.list[state.currentPage].title,
                        style = myTypography.w800,
                        fontSize = dpToSp(dp = 26.dp),
                        color = DefaultBlack
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.list[state.currentPage].content,
                        style = myTypography.w400,
                        fontSize = dpToSp(dp = 14.dp),
                        color = DefaultBlack,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(92.dp))
                    FlatButton(
                        modifier = Modifier.fillMaxWidth().aspectRatio(6.5f),
                        text = state.list[state.currentPage].buttonName,
                        backgroundColor = Color(0xff4985F8),
                        onClick = {
                            welcomeScreenClickListener.onClickButton()
                        }
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }

    private fun initClickListeners() {
        welcomeScreenClickListener = object : WelcomeScreenClickListener {
            override fun onClickButton() {
                viewModel.nextPage()
            }
        }
    }
}