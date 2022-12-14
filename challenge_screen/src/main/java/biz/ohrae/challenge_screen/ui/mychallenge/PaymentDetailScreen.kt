package biz.ohrae.challenge_screen.ui.mychallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import biz.ohrae.challenge.ui.components.header.BackButton
import biz.ohrae.challenge.ui.components.list_item.getLabelText
import biz.ohrae.challenge.ui.components.list_item.getLabelTextColor
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge.ui.theme.dpToSp
import biz.ohrae.challenge.ui.theme.myTypography
import biz.ohrae.challenge_repo.model.user.PaymentHistoryData
import biz.ohrae.challenge_repo.util.prefs.Utils
import biz.ohrae.challenge_screen.ui.participation.ParticipationScreen


@Preview(
    widthDp = 360,
    heightDp = 720,
    showBackground = true
)
@Composable
fun PaymentDetailScreen(
    paymentHistoryData: PaymentHistoryData? = null
) {
    val isCancel = paymentHistoryData?.type == "deposit"
    val type = paymentHistoryData?.type.toString()
    BackButton(onBack = { }, "결제 상세")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DefaultWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xfff8f8f8))
                .padding(24.dp, 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier,
                text = "이용내역",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp),
                color = Color(0xff707070)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = getLabelText(type),
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp),
                color = getLabelTextColor(type),
                textAlign = TextAlign.Right,
            )
        }
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "챌린지",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = paymentHistoryData?.challenge?.goal ?: "",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "기간",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text = "${Utils.convertDate6(paymentHistoryData?.challenge?.start_date.toString())}" +
                            " ~ " +
                            "${Utils.convertDate6(paymentHistoryData?.challenge?.end_date.toString())}",
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xfff8f8f8))
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 18.dp),
                text = "결제내역",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp),
                color = Color(0xff707070)
            )
        }
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "결제수단",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                paymentHistoryData?.let{
                    Text(
                        text = Utils.getPaidMethod(it),
                        style = myTypography.bold,
                        fontSize = dpToSp(dp = 14.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "카드결제",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text =
                    "${Utils.numberToString(paymentHistoryData?.paid_amount.toString())}원",
                    style = myTypography.extraBold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "리워즈결제",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text =
                    "${Utils.numberToString(paymentHistoryData?.rewards_amount.toString())}원",
                    style = myTypography.extraBold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(24.dp, 0.dp)
                .background(Color(0xfffafafa))
        )
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "총 결제금액",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text =
                    "${Utils.numberToString(paymentHistoryData?.amount.toString())}원",
                    style = myTypography.extraBold,
                    fontSize = dpToSp(dp = 14.dp),
                    color = getLabelTextColor(type)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "결제일시",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text = Utils.convertDate8(paymentHistoryData?.created_date.toString()),
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
        }
//        if(isCancel){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xfff8f8f8))
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 18.dp),
                text = "환급내역",
                style = myTypography.bold,
                fontSize = dpToSp(dp = 14.dp),
                color = Color(0xff707070)
            )
        }
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "카드 결제 취소",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )

                Text(
                    text =
                    if (paymentHistoryData?.paid_amount.toString() == "0")
                        "${Utils.numberToString(paymentHistoryData?.paid_amount.toString())}원"
                    else
                        "-${Utils.numberToString(paymentHistoryData?.paid_amount.toString())}원",
                    style = myTypography.extraBold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "리워즈 결제 취소",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text =
                    if (paymentHistoryData?.rewards_amount.toString() == "0")
                        "${Utils.numberToString(paymentHistoryData?.rewards_amount.toString())}원"
                    else
                        "-${Utils.numberToString(paymentHistoryData?.rewards_amount.toString())}원",
                    style = myTypography.extraBold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(24.dp, 0.dp)
                .background(Color(0xfffafafa))
        )
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "총 취소 금액",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text =
                    if (paymentHistoryData?.amount.toString() == "0")
                        "${Utils.numberToString(paymentHistoryData?.amount.toString())}원"
                    else
                        "-${Utils.numberToString(paymentHistoryData?.amount.toString())}원",
                    style = myTypography.extraBold,
                    fontSize = dpToSp(dp = 14.dp),
                    color = getLabelTextColor(type)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "결제 취소 일시",
                    style = myTypography.w700,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Color(0xff707070)
                )
                Text(
                    text = Utils.convertDate8(paymentHistoryData?.created_date.toString()),
                    style = myTypography.bold,
                    fontSize = dpToSp(dp = 14.dp),
                )
            }
        }
//        }
    }
}