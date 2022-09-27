package biz.ohrae.challenge_screen.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge_screen.model.register.ChallengeOpenState
import biz.ohrae.challenge.ui.components.button.FlatBottomButton
import biz.ohrae.challenge.ui.theme.DefaultBlack
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_screen.ui.register.RegisterActivity.Companion.AUTH


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun RegisterAuthScreen(
    challengeOpenState: ChallengeOpenState = ChallengeOpenState.mock(),
    clickListener: RegisterClickListener? = null,

    ) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(challengeOpenState.challengeRadioOptions[0].radioTitleEn) }

    Column(modifier = Modifier.padding(24.dp, 0.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "챌린지 인증",
            style = myTypography.w700,
            fontSize = dpToSp(dp = 20.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column() {
            challengeOpenState.challengeRadioOptions.forEach { radioOption ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (radioOption.radioTitleEn == selectedOption),
                            onClick = {
                                onOptionSelected(radioOption.radioTitleEn)
                                clickListener?.onClickSelectedAuth(radioOption.radioTitleEn)
                            }),
                    verticalAlignment = CenterVertically
                ) {
                    RadioButton(
                        modifier = Modifier.size(20.dp).align(Top),
                        selected = (radioOption.radioTitleEn == selectedOption),
                        onClick = { onOptionSelected(radioOption.radioTitleEn) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xff005bad),
                            unselectedColor = Color(0xffc7c7c7),
                        ),
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Column() {
                        Text(
                            text = radioOption.radioTitle,
                            modifier = Modifier.fillMaxWidth(),
                            style = if (radioOption.radioTitleEn == selectedOption) myTypography.bold else myTypography.w500,
                            fontSize = dpToSp(dp = 14.dp),
                            color = if (radioOption.radioTitleEn == selectedOption) DefaultBlack else Color(
                                0xff6c6c6c
                            ),
                        )
                        Spacer(modifier = Modifier.height(9.dp))

                        Text(
                            text = radioOption.radioContent,
                            modifier = Modifier.fillMaxWidth(),
                            style = myTypography.w500,
                            fontSize = dpToSp(dp = 12.dp),
                            color = Color(0xff6c6c6c)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
        }

    }
    Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {

        FlatBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(6f),
            text = "다음(${AUTH}/4)",
            onClick = { clickListener?.onClickAuthNext(selectedOption) }
        )
    }
}




