package biz.ohrae.challenge_repo.data.remote

import com.google.gson.JsonObject
import retrofit2.http.*

interface ApiServiceForBase {
    @POST(BaseRoutes.UID_CHECK)
    suspend fun uidCheck(
        @Header("brand_code") brand_code: String?,
        @Body body: JsonObject?
    ): NetworkResponse<Result, Error>

    @POST(BaseRoutes.AUTH)
    suspend fun auth(
        @Header("brand_code") brand_code: String?,
        @Header("device_uid") device_uid: String?,
        @Body body: JsonObject?
    ): NetworkResponse<Result, Error>

    @POST(BaseRoutes.AUTH_CHECK)
    suspend fun authCheck(
        @Header("brand_code") brand_code: String?,
        @Body body: JsonObject?
    ): NetworkResponse<Result, Error>

    @GET(BaseRoutes.RELATE)
    suspend fun relate(
        @Header("brand_code") brand_code: String?,
        @Header("from-device") from_device: String? = "mobile",
        @Header("x-access-token") access_token: String?,
        @Path("customer_id") customer_id: String,
    ): NetworkResponse<Result, Error>
}