package biz.ohrae.challenge_repo.data.remote

import com.google.gson.JsonObject

data class Result2(
    var success: Boolean = false,
    var dataset: JsonObject? = null,
    var code: String? = null,
    var message: String? = null
)
