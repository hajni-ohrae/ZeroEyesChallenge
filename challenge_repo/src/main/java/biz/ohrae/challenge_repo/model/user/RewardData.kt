package biz.ohrae.challenge_repo.model.user

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