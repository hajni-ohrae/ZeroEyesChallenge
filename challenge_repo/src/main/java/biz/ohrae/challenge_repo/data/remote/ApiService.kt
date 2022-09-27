package biz.ohrae.challenge_repo.data.remote

import com.google.gson.JsonObject
import retrofit2.http.*

interface ApiService {
    @POST(Routes.LOGIN)
    suspend fun login(@Body body: JsonObject?): NetworkResponse<Result2, Error>

    @GET(Routes.AUTH_TOKEN_CHECK)
    suspend fun authTokenCheck(
        @Header("x-access-token") accessToken: String,
    ): NetworkResponse<Result, Error>

    @GET(Routes.AUTH_TOKEN_REFRESH)
    suspend fun authTokenRefresh(): NetworkResponse<Result, Error>

    @GET(Routes.GET_ALL_CHALLENGE)
    suspend fun getAllChallenge(
        @Header("x-access-token") accessToken: String,
        @Query("payment_type") paymentType: String,
        @Query("verification_period_type") verificationPeriodType: String,
        @Query("per_week") perWeek: String? = "",
        @Query("period") period: String? = "",
        @Query("is_adult_only") isAdultOnly: String? = "",
    ): NetworkResponse<Result2, Error>

    @GET(Routes.GET_CHALLENGE)
    suspend fun getChallenge(
        @Header("x-access-token") accessToken: String,
        @Path("challenge_id") challengeId: String,
    ): NetworkResponse<Result2, Error>

    @POST(Routes.CREATE_CHALLENGE)
    suspend fun createChallenge(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.REGISTER_CHALLENGE)
    suspend fun registerChallenge(
        @Header("x-access-token") accessToken: String,
        @Body body: JsonObject?
    ): NetworkResponse<Result, Error>

    @POST(Routes.FAVORITE_CHALLENGE)
    suspend fun favoriteChallenge(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.REQUEST_PAYMENT)
    suspend fun requestPayment(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.COMPLETE_PAYMENT)
    suspend fun completePayment(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.REFUND_PAYMENT)
    suspend fun refundPayment(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @GET(Routes.GET_ALL_WARNING)
    suspend fun getAllWarning(): NetworkResponse<Result, Error>

    @POST(Routes.SET_WARNING)
    suspend fun setWarning(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.CANCEL_WARNING)
    suspend fun cancelWarning(
        @Path("redcard_id") redCardId: String,
        @Body body: JsonObject?
    ): NetworkResponse<Result, Error>

    @GET(Routes.GET_ALL_BLOCK)
    suspend fun getAllBlock(): NetworkResponse<Result, Error>

    @POST(Routes.SET_BLOCK_USER)
    suspend fun setBlockUser(
        @Path("user_id") userId: String,
        @Body body: JsonObject?
    ): NetworkResponse<Result, Error>

    @POST(Routes.CANCEL_BLOCK_USER)
    suspend fun cancelBlockUser(
        @Path("user_id") userId: String,
        @Body body: JsonObject?
    ): NetworkResponse<Result, Error>

    @POST(Routes.CREATE_USER)
    suspend fun createUser(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.CREATE_USER_SERVICE)
    suspend fun createUserService(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.CREATE_RELATE_SERVICE)
    suspend fun createRelateService(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.CREATE_SERVICE_OWNER)
    suspend fun createServiceOwner(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.UPLOAD_USER_PROFILE)
    suspend fun uploadUserProfile(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.VERIFY)
    suspend fun verify(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.SERVICE_VERIFY)
    suspend fun serviceVerify(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @GET(Routes.GET_ALL_REPORT)
    suspend fun getAllReport(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.REGISTER_REPORT)
    suspend fun registerReport(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.CREATE_REPORT)
    suspend fun createReport(@Body body: JsonObject?): NetworkResponse<Result, Error>
}