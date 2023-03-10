package biz.ohrae.challenge_repo.ui.main

import androidx.compose.runtime.snapshots.SnapshotStateList
import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import biz.ohrae.challenge_repo.model.FlowResult
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import biz.ohrae.challenge_repo.util.PagerMeta
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject


class ChallengeMainRepo @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val prefs: SharedPreference
) {
    suspend fun getChallenges(
        paymentType: String,
        verificationPeriodType: String,
        per_week: String,
        period: String,
        age_limit_type: String,
        is_like: String,
        page: Int = 1,
        perPage: Int = 10,
    ): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        Timber.e("getAllChallenge : ${paymentType}, ${verificationPeriodType}, ${per_week},${period},${age_limit_type}")

        val response =
            apiService.getAllChallenge(
                accessToken = accessToken.toString(),
                page = page.toInt(),
                perPage = perPage.toInt(),
                paymentType = paymentType,
                verificationPeriodType = verificationPeriodType,
                isLike = is_like,
                perWeek = per_week,
                period = period,
                ageLimitType = age_limit_type
            )

        when (response) {
            is NetworkResponse.Success -> {
                val gson = Gson()
                val isSuccess = response.body.success
                if (isSuccess) {
                    val dataset = response.body.dataset
                    dataset?.let {

                        val dataSet: JsonElement = dataset.getAsJsonArray("array")!!.asJsonArray
                        val pager =
                            gson.fromJson(dataset.get("meta").toString(), PagerMeta::class.java)

                        val listType = object : TypeToken<SnapshotStateList<ChallengeData?>?>() {}.type
                        val challengeList = gson.fromJson<SnapshotStateList<ChallengeData>>(dataSet, listType)

                        return flow {
                            emit(FlowResult(challengeList, "", "", pager))
                        }
                    } ?: run {
                        return flow {
                            emit(FlowResult(null, "", "data is null"))
                        }
                    }
                } else {
                    return flow {
                        emit(FlowResult(null, response.body.code, response.body.message))
                    }
                }

            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", response.errorMessage))
                }
            }
        }
    }

    suspend fun getUserChallengeList(
        type: String="",
        page: Int = 1,
    ): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token
        val response =
            apiService.getUserChallengeList(accessToken.toString(), page, 10, type)
        when (response) {
            is NetworkResponse.Success -> {
                val gson = Gson()
                val isSuccess = response.body.success
                if (isSuccess) {
                    val dataset = response.body.dataset
                    dataset?.let {
                        val dataSet: JsonElement = dataset?.getAsJsonArray("array")!!.asJsonArray
                        val pager =
                            gson.fromJson(dataset.get("meta").toString(), PagerMeta::class.java)

                        val listType = object : TypeToken<SnapshotStateList<ChallengeData?>?>() {}.type
                        val userChallengeList =
                            gson.fromJson<SnapshotStateList<ChallengeData>>(dataSet, listType)
                        return flow {
                            emit(FlowResult(userChallengeList, "", "",pager))
                        }
                    } ?: run {
                        return flow {
                            emit(FlowResult(null, "", ""))
                        }
                    }
                } else {
                    return flow {
                        emit(FlowResult(null, response.body.code, response.body.message))
                    }
                }

            }
            else -> {
                return flow {
                    emit(FlowResult(null, "", ""))
                }
            }
        }
    }
}