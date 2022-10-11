package biz.ohrae.challenge_repo.data.remote

import com.google.gson.JsonObject
import okhttp3.MultipartBody
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
        @Header("page") page: Int,
        @Header("per_page") perPage: Int,
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
    suspend fun createChallenge(@Body body: JsonObject?): NetworkResponse<Result2, Error>

    @POST(Routes.REGISTER_CHALLENGE)
    suspend fun registerChallenge(
        @Header("x-access-token") accessToken: String,
        @Body body: JsonObject?
    ): NetworkResponse<Result2, Error>

    @POST(Routes.FAVORITE_CHALLENGE)
    suspend fun favoriteChallenge(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.CANCEL_CHALLENGE)
    suspend fun cancelChallenge(
        @Header("x-access-token") accessToken: String,
        @Path("challenge_id") challengeId: String,
        @Path("user_id") userId: String,
    ): NetworkResponse<Result, Error>

    @GET(Routes.GET_USERS_BY_CHALLENGE)
    suspend fun getUsersByChallenge(
        @Header("x-access-token") accessToken: String,
        @Header("page") page: Int,
        @Header("per_page") count: Int,
        @Path("challenge_id") challengeId: String,
    ): NetworkResponse<Result2, Error>

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

    @POST(Routes.GET_RADCARD_LIST)
    suspend fun getRedCardList(
        @Header("x-access-token") accessToken: String,
        @Header("page") page: Int,
        @Header("per_page") count: Int,
        ): NetworkResponse<Result, Error>

    @GET(Routes.GET_ALL_BLOCK)
    suspend fun getAllBlock(
        @Header("x-access-token") accessToken: String
    ): NetworkResponse<Result, Error>

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
    suspend fun verify(
        @Header("x-access-token") accessToken: String,
        @Body body: JsonObject?
    ): NetworkResponse<Result, Error>

    @POST(Routes.SERVICE_VERIFY)
    suspend fun serviceVerify(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @GET(Routes.GET_ALL_REPORT)
    suspend fun getAllReport(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.REGISTER_REPORT)
    suspend fun registerReport(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.CREATE_REPORT)
    suspend fun createReport(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.GET_REGISTER_REPORT)
    suspend fun getRegisterReport(
        @Header("x-access-token") accessToken: String
    ): NetworkResponse<Result, Error>

    @Multipart
    @POST(Routes.CHALLENGE_UPLOAD_IMAGE)
    suspend fun uploadChallengeImage(
        @Part body: MultipartBody.Part,
        @Path("challenge_id") challengeId: String,
    ): NetworkResponse<Result, Error>

    @GET(Routes.GET_USER_CHALLENGE_LIST)
    suspend fun getUserChallengeList(
        @Header("x-access-token") accessToken: String
    ): NetworkResponse<Result2, Error>
}