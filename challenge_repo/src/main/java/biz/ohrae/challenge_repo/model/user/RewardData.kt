package biz.ohrae.challenge_repo.model.user

import biz.ohrae.challenge.model.filter.FilterItem
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.model.detail.InChallenge


data class RewardData(
    val id: Int,
    val type: String,
    val amount: Int,
    val free_rewards: Int,
    val created_date: String,
    val updated_date: String,
    val challenge: ChallengeData,
    val inChallenge: InChallenge
) {

}

data class RewardFilter(
    val rewardFilter: List<FilterItem>,
    var selectRewardFilter: String,
) {
    companion object {
        fun mock() = RewardFilter(
            rewardFilter = listOf(
                FilterItem("전체", "all"),
                FilterItem("적립", "earn"),
                FilterItem("사용", "use"),
                FilterItem("출금", "transfer"),
                FilterItem("소멸", "expire"),
                FilterItem("환급", "refund"),
                FilterItem("취소", "cancel"),
            ),
            selectRewardFilter = "all"
        )
    }
}