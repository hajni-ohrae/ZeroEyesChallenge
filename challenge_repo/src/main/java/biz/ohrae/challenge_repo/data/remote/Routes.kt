package biz.ohrae.challenge_repo.data.remote

object Routes {
    const val HOST_NAME = "https://dev.mooin.kr/challenge/"

    // 로그인 & 인증
    const val LOGIN = "/api/auth/service/login"
    const val AUTH_TOKEN_CHECK = "/api/auth/token/check"
    const val AUTH_TOKEN_REFRESH = "/api/auth/token/refresh"

    // 챌린지
    const val GET_CHALLENGE = "/api/challenge/get"
    const val CREATE_CHALLENGE = "/api/challenge/create"
    const val REGISTER_CHALLENGE = "/api/challenge/register"
    const val FAVORITE_CHALLENGE = "/api/challenge/favorite"

    // 결제
    const val REQUEST_PAYMENT = "/api/payment/request"
    const val COMPLETE_PAYMENT = "/api/payment/complete"
    const val REFUND_PAYMENT = "/api/payment/refund"

    // 규정 (레드카드)
    const val GET_ALL_WARNING = "/api/regulation/warn/get/all"
    const val SET_WARNING = "/api/regulation/warn"
    const val CANCEL_WARNING = "/api/regulation/warn/cancel/{redcard_id}"

    // 규정 (이용정지)
    const val GET_ALL_BLOCK = "/api/regulation/block/get/all"
    const val SET_BLOCK_USER = "/api/regulation/block/{user_id}"
    const val CANCEL_BLOCK_USER = "/api/regulation/block/cancel/{user_id}"

    // 사용자
    const val CREATE_USER = "/api/user/create"
    const val CREATE_USER_SERVICE = "/api/user/service/create"
    const val CREATE_RELATE_SERVICE = "/api/user/service/relate"
    const val CREATE_SERVICE_OWNER = "/api/user/service/owner/create"
    const val UPLOAD_USER_PROFILE = "/api/user/upload/profile"

    // 챌린지 인증
    const val VERIFY = "/api/verification/verify"
    const val SERVICE_VERIFY = "/api/verification/service/verify"

    // 챌린지 인증 신고
    const val GET_ALL_REPORT = "/api/verification/report/get/all"
    // 신고 이유 등록
    const val REGISTER_REPORT = "/api/verification/report/register/code"
    // 신고
    const val CREATE_REPORT = "/api/verification/report/create"
}