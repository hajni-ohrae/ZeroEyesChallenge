package biz.ohrae.challenge.model.participation

data class ParticipationState(
    val userId: String,
    val challengeId: String,
    val paid_amount: String,
    val rewards_amount: String,
    val verification_type: String,
    val deposit_amount: Int,
    val reward_amount: Int
) {

}