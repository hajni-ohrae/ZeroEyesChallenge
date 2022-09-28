package biz.ohrae.challenge_repo.data.remote

object Routes {
    const val HOST_NAME = "https://dev.mooin.kr/"

    private const val CHALLENGE = "/challenge"

    // 로그인 & 인증
    const val LOGIN = "${CHALLENGE}/api/auth/service/login"
    const val AUTH_TOKEN_CHECK = "${CHALLENGE}/api/auth/token/check"
    const val AUTH_TOKEN_REFRESH = "${CHALLENGE}/api/auth/token/refresh"

    // 챌린지
    const val GET_ALL_CHALLENGE = "${CHALLENGE}/api/challenge/get/all"
    const val GET_CHALLENGE = "${CHALLENGE}/api/challenge/get/{challenge_id}"
    const val CREATE_CHALLENGE = "${CHALLENGE}/api/challenge/create"
    const val REGISTER_CHALLENGE = "${CHALLENGE}/api/challenge/register"
    const val FAVORITE_CHALLENGE = "${CHALLENGE}/api/challenge/favorite"

    // 결제
    const val REQUEST_PAYMENT = "${CHALLENGE}/api/payment/request"
    const val COMPLETE_PAYMENT = "${CHALLENGE}/api/payment/complete"
    const val REFUND_PAYMENT = "${CHALLENGE}/api/payment/refund"

    // 규정 (레드카드)
    const val GET_ALL_WARNING = "${CHALLENGE}/api/regulation/warn/get/all"
    const val SET_WARNING = "${CHALLENGE}/api/regulation/warn"
    const val CANCEL_WARNING = "${CHALLENGE}/api/regulation/warn/cancel/{redcard_id}"

    // 규정 (이용정지)
    const val GET_ALL_BLOCK = "${CHALLENGE}/api/regulation/block/get/all"
    const val SET_BLOCK_USER = "${CHALLENGE}/api/regulation/block/{user_id}"
    const val CANCEL_BLOCK_USER = "${CHALLENGE}/api/regulation/block/cancel/{user_id}"

    // 사용자
    const val CREATE_USER = "${CHALLENGE}/api/user/create"
    const val CREATE_USER_SERVICE = "${CHALLENGE}/api/user/service/create"
    const val CREATE_RELATE_SERVICE = "${CHALLENGE}/api/user/service/relate"
    const val CREATE_SERVICE_OWNER = "${CHALLENGE}/api/user/service/owner/create"
    const val UPLOAD_USER_PROFILE = "${CHALLENGE}/api/user/upload/profile"

    // 챌린지 인증
    const val VERIFY = "${CHALLENGE}/api/verification/verify"
    const val SERVICE_VERIFY = "${CHALLENGE}/api/verification/service/verify"

    // 챌린지 인증 신고
    const val GET_ALL_REPORT = "${CHALLENGE}/api/verification/report/get/all"
    // 신고 이유 등록
    const val REGISTER_REPORT = "${CHALLENGE}/api/verification/report/register/code"
    // 신고
    const val CREATE_REPORT = "${CHALLENGE}/api/verification/report/create"

    // 이미지 업로드
    const val CHALLENGE_UPLOAD_IMAGE = "${CHALLENGE}/api/challenge/upload/image/{challenge_id}"
}