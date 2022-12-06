package biz.ohrae.challenge_repo.data.remote

object Routes {
    const val HOST_NAME = "https://challenge-dev.mooin.kr/"
    const val PAYMENT_HOST_NAME = "https://challenge-dev.mooin.kr/"

    private const val CHALLENGE = ""

    // 로그인 & 인증
    const val LOGIN = "${CHALLENGE}/api/auth/service/login"
    const val AUTH_TOKEN_CHECK = "${CHALLENGE}/api/auth/token/check"
    const val AUTH_TOKEN_REFRESH = "${CHALLENGE}/api/auth/token/refresh"

    // 챌린지
    const val GET_ALL_CHALLENGE = "${CHALLENGE}/api/challenge/get/all"
    const val GET_CHALLENGE = "${CHALLENGE}/api/challenge/get/{challenge_id}"
    const val CREATE_CHALLENGE = "${CHALLENGE}/api/challenge/create"
    const val REGISTER_CHALLENGE = "${CHALLENGE}/api/challenge/register"
    const val FAVORITE_CHALLENGE = "${CHALLENGE}/api/challenge/like"
    const val GET_USER_CHALLENGE_LIST = "${CHALLENGE}/api/challenge/user/get/all"
    const val CANCEL_CHALLENGE = "${CHALLENGE}/api/challenge/cancel/{challenge_id}"
    const val GET_USERS_BY_CHALLENGE = "${CHALLENGE}/api/user/challenge/get/{challenge_id}"
    const val GET_CHALLENGE_RESULT = "${CHALLENGE}/api/challenge/result/get/{challenge_id}"

    // 결제
    const val REQUEST_PAYMENT = "/api/payment/request"
    const val COMPLETE_PAYMENT = "${CHALLENGE}/api/payment/complete"
    const val REFUND_PAYMENT = "${CHALLENGE}/api/payment/refund"
    const val GET_PAYMENT_HISTORY ="${CHALLENGE}/api/payment/history/get/all"

    // 리워즈
    const val GET_REWARD_HISTORY ="${CHALLENGE}/api/rewards/history/get/all"

    // 규정 (레드카드)
    const val GET_ALL_WARNING = "${CHALLENGE}/api/regulation/warn/get/all"
    const val SET_WARNING = "${CHALLENGE}/api/regulation/warn"
    const val CANCEL_WARNING = "${CHALLENGE}/api/regulation/warn/cancel/{redcard_id}"
    const val GET_RADCARD_LIST = "${CHALLENGE}/api/regulation/redcard/get/all"

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
    const val GET_USER_DATA = "${CHALLENGE}/api/user/get/detail"

    // 챌린지 인증
    const val VERIFY = "${CHALLENGE}/api/verification/verify"
    const val SERVICE_VERIFY = "${CHALLENGE}/api/verification/service/verify"
    const val GET_VERIFY_LIST = "${CHALLENGE}/api/verification/challenge/get/{challenge_id}"

    // 챌린지 인증 신고
    const val GET_ALL_REPORT = "${CHALLENGE}/api/verification/report/get/all"
    // 신고 이유 등록
    const val REGISTER_REPORT = "${CHALLENGE}/api/verification/report/register/code"
    // 신고
    const val CREATE_REPORT = "${CHALLENGE}/api/verification/report/create"
    //신고 이유 조회
    const val GET_REGISTER_REPORT = "${CHALLENGE}/api/verification/report/code/get/all"

    // 챌린지 이미지 업로드
    const val CHALLENGE_UPLOAD_IMAGE = "${CHALLENGE}/api/file/upload/image/challenge"

    // 사용자 이미지 업로드
    const val USER_UPLOAD_IMAGE = "${CHALLENGE}/api/file/upload/image/user"

    // 사용자 프로필 업데이트
    const val UPDATE_USER_PROFILE = "${CHALLENGE}/api/user/update"

    // 피드 좋아요
    const val FEED_LIKE = "${CHALLENGE}/api/verification/feed/like"

    // 닉네임 중복체크
    const val CHECK_NICKNAME = "${CHALLENGE}/api/user/check/nickname"

    // 은행 코드 조회
    const val RETRIEVE_BANK_CODES = "${CHALLENGE}/api/banking/code/get/all"

    // 은행 계좌 등록
    const val REGISTER_BANK_ACCOUNT = "${CHALLENGE}/api/banking/register"

    // 출금 신청
    const val TRANSFER_REWARDS = "${CHALLENGE}/api/banking/transfer"
}