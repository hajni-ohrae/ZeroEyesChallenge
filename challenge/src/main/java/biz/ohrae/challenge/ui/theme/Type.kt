package biz.ohrae.challenge.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import biz.ohrae.challenge.R

val fonts = FontFamily(
    Font(R.font.nanum_square_regular, weight = FontWeight.Normal),
    Font(R.font.nanum_square_bold, weight = FontWeight.Bold),
    Font(R.font.nanum_square_light, weight = FontWeight.Light),
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )


    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

data class MyTypography(
    val default: TextStyle = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
    ),
    val w100: TextStyle = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W100,
    ),
    val w200: TextStyle = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W200,
    ),
    val w300: TextStyle = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W300,
    ),
    val w400: TextStyle = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W400,
    ),
    val w500: TextStyle = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500,
    ),
    val w600: TextStyle = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W600,
    ),
    val w700: TextStyle = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W700,
    ),
    val w800: TextStyle = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W800,
    ),
    val w900: TextStyle = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W900,
    ),
)
val myTypography = MyTypography()

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }