package biz.ohrae.challenge_repo.ui.main

import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import timber.log.Timber
import javax.inject.Inject

class UserRepo @Inject constructor(private val apiService: ApiService) {
    suspend fun login() {
        val jsonObject = JsonObject()
        val serviceIds = JsonArray()
        serviceIds.add("5c0a3780-f5b2-11ec-8384-e516c5dd9af3")
        serviceIds.add("65cb9550-f2c6-11ec-b367-bbcce7d7ae2f")

        jsonObject.addProperty("serviceType", "ze_study")
        jsonObject.add("service_ids", serviceIds)

        val response = apiService.login(jsonObject)

        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                if (isSuccess) {

                } else {
                    Timber.e("error code : ${response.body.code}")
                }
            }
            else -> {

            }
        }
    }

    suspend fun createServiceUser() {
        val jsonObject = JsonObject()
        val serviceIds = JsonArray()
        serviceIds.add("5c0a3780-f5b2-11ec-8384-e516c5dd9af3")
        serviceIds.add("65cb9550-f2c6-11ec-b367-bbcce7d7ae2f")

        jsonObject.addProperty("name", "김경봉")
        jsonObject.addProperty("nickname", "Roger")
        jsonObject.addProperty("phone_number", "01095357292")
        jsonObject.addProperty("service_type", "ze_study")
        jsonObject.add("service_ids", serviceIds)

        val response = apiService.createUserService(jsonObject)
    }
}