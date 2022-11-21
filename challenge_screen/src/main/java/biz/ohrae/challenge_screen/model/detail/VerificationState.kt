package biz.ohrae.challenge_screen.model.detail

import biz.ohrae.challenge_repo.model.detail.Verification

data class VerificationState(
    val successCount: Int,
    val remainCount: Int,
    val failCount: Int,
    val achievement: String,
    var verifications: List<Verification>? = null
)
