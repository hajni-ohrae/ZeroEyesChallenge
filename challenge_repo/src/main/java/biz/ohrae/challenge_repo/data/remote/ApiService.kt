package biz.ohrae.challenge_repo.data.remote

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST(Routes.LOGIN)
    suspend fun login(
        @Header("x-access-token") accessToken: String,
        @Body body: JsonObject?,
    ): NetworkResponse<Result2, Error>

    @GET(Routes.AUTH_TOKEN_CHECK)
    suspend fun authTokenCheck(
        @Header("x-access-token") accessToken: String,
    ): NetworkResponse<Result, Error>

    @GET(Routes.AUTH_TOKEN_REFRESH)
    suspend fun authTokenRefresh(
        @Header("x-refresh-token") refreshToken: String
    ): NetworkResponse<Result, Error>

    @GET(Routes.GET_ALL_CHALLENGE)
    suspend fun getAllChallenge(
        @Header("x-access-token") accessToken: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("payment_type") paymentType: String,
        @Query("verification_period_type") verificationPeriodType: String,
        @Query("is_like") isLike: String,
        @Query("per_week") perWeek: String,
        @Query("period") period: String,
        @Query("is_adult_only") isAdultOnly: String,
    ): NetworkResponse<Result2, Error>

    @GET(Routes.GET_CHALLENGE)
    suspend fun getChallenge(
        @Header("x-access-token") accessToken: String,
        @Path("challenge_id") challengeId: String,
    ): NetworkResponse<Result2, Error>

    @POST(Routes.CREATE_CHALLENGE)
    suspend fun createChallenge(
        @Header("x-access-token") accessToken: String,
        @Body body: JsonObject?
    ): NetworkResponse<Result2, Error>

    @POST(Routes.REGISTER_CHALLENGE)
    suspend fun registerChallenge(
        @Header("x-access-token") accessToken: String,
        @Body body: JsonObject?
    ): NetworkResponse<Result2, Error>

    @POST(Routes.FAVORITE_CHALLENGE)
    suspend fun favoriteChallenge(
        @Header("x-access-token") accessToken: String, @Body body: JsonObject?,
    ): NetworkResponse<Result, Error>

    @DELETE(Routes.CANCEL_CHALLENGE)
    suspend fun cancelChallenge(
        @Header("x-access-token") accessToken: String,
        @Path("challenge_id") challengeId: String,
    ): NetworkResponse<Result, Error>

    @GET(Routes.GET_USERS_BY_CHALLENGE)
    suspend fun getUsersByChallenge(
        @Header("x-access-token") accessToken: String,
        @Path("challenge_id") challengeId: String,
        @Query("page") page: Int,
        @Query("per_page") count: Int,
    ): NetworkResponse<Result2, Error>

    @GET(Routes.GET_CHALLENGE_RESULT)
    suspend fun getChallengeResult(
        @Header("x-access-token") accessToken: String,
        @Path("challenge_id") challengeId: String,
    ): NetworkResponse<Result2, Error>

    @POST(Routes.REQUEST_PAYMENT)
    suspend fun requestPayment(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.COMPLETE_PAYMENT)
    suspend fun completePayment(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.REFUND_PAYMENT)
    suspend fun refundPayment(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.GET_PAYMENT_HISTORY)
    suspend fun getPaymentHistory(
        @Header("x-access-token") accessToken: String,
        @Query("page") page: Int,
        @Query("per_page") count: Int,
    ): NetworkResponse<Result, Error>

    @GET(Routes.GET_REWARD_HISTORY)
    suspend fun getRewardHistory(
        @Header("x-access-token") accessToken: String,
        @Query("page") page: Int,
        @Query("per_page") count: Int,
    ): NetworkResponse<Result2, Error>

    @GET(Routes.GET_ALL_WARNING)
    suspend fun getAllWarning(): NetworkResponse<Result, Error>

    @POST(Routes.SET_WARNING)
    suspend fun setWarning(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.CANCEL_WARNING)
    suspend fun cancelWarning(
        @Path("redcard_id") redCardId: String,
        @Body body: JsonObject?
    ): NetworkResponse<Result, Error>

    @GET(Routes.GET_RADCARD_LIST)
    suspend fun getRedCardList(
        @Header("x-access-token") accessToken: String,
        @Query("page") page: Int,
        @Query("per_page") count: Int,
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
    suspend fun createUserService(
        @Header("x-access-token") accessToken: String,
        @Body body: JsonObject?
    ): NetworkResponse<Result, Error>

    @POST(Routes.CREATE_RELATE_SERVICE)
    suspend fun createRelateService(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.CREATE_SERVICE_OWNER)
    suspend fun createServiceOwner(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.UPLOAD_USER_PROFILE)
    suspend fun uploadUserProfile(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @GET(Routes.GET_USER_DATA)
    suspend fun getUserData(
        @Header("x-access-token") accessToken: String
    ): NetworkResponse<Result2, Error>

    @Multipart
    @POST(Routes.VERIFY)
    suspend fun verify(
        @Header("x-access-token") accessToken: String,
        @Part file: MultipartBody.Part,
        @Part("challenge_id") challengeId: RequestBody,
        @Part("user_id") userId: RequestBody,
        @Part("type") type: RequestBody,
        @Part("comment") comment: RequestBody,
    ): NetworkResponse<Result, Error>

    @POST(Routes.SERVICE_VERIFY)
    suspend fun serviceVerify(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.GET_VERIFY_LIST)
    suspend fun getVerifyList(
        @Path("challenge_id") challengeId: String,
        @Header("x-access-token") accessToken: String,
        @Body body: JsonObject?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("payment_type") paymentType: String = "",
        @Query("verification_period_type") verificationPeriodType: String = "",
        @Query("per_week") perWeek: String? = "",
        @Query("period") period: String? = "",
        @Query("is_like") isLike: String? = "",
        @Query("is_adult_only") isAdultOnly: String? = "",
    ): NetworkResponse<Result2, Error>

    @GET(Routes.GET_ALL_REPORT)
    suspend fun getAllReport(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.REGISTER_REPORT)
    suspend fun registerReport(@Body body: JsonObject?): NetworkResponse<Result, Error>

    @POST(Routes.CREATE_REPORT)
    suspend fun createReport(
        @Header("x-access-token") accessToken: String, @Body body: JsonObject?
    ): NetworkResponse<Result, Error>

    @GET(Routes.GET_REGISTER_REPORT)
    suspend fun getRegisterReport(
        @Header("x-access-token") accessToken: String
    ): NetworkResponse<Result, Error>

    @Multipart
    @POST(Routes.CHALLENGE_UPLOAD_IMAGE)
    suspend fun uploadChallengeImage(
        @Header("x-access-token") accessToken: String,
        @Part file: MultipartBody.Part,
    ): NetworkResponse<Result2, Error>

    @GET(Routes.GET_USER_CHALLENGE_LIST)
    suspend fun getUserChallengeList(
        @Header("x-access-token") accessToken: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("status") status: String
    ): NetworkResponse<Result2, Error>

    @POST(Routes.FEED_LIKE)
    suspend fun feedLike(
        @Header("x-access-token") accessToken: String,
        @Body body: JsonObject?,
    ): NetworkResponse<Result, Error>

}