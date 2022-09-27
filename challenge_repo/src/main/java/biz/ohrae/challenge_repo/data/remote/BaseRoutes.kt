package biz.ohrae.challenge_repo.data.remote

object BaseRoutes {
    private const val HOST_NAME = "https://dev.mooin.kr"

    const val AUTH = "$HOST_NAME/api/auth/customer/signin"
    const val AUTH_CHECK = "$HOST_NAME/api/auth/customer/check"
    const val UID_CHECK = "$HOST_NAME/api/auth/customer/duid"
    const val RELATE = "$HOST_NAME/api/customer/{customer_id}/related"
}