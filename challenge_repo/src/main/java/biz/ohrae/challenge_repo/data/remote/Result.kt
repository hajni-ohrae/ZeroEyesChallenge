package biz.ohrae.challenge_repo.data.remote

import com.google.gson.JsonArray

data class Result(
    var success: Boolean = false,
    var dataset: JsonArray? = null,
    var code: String? = null,
    var message: String? = null
)
