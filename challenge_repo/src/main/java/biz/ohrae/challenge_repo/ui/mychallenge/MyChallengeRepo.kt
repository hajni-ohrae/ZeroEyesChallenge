package biz.ohrae.challenge_repo.ui.mychallenge

import biz.ohrae.challenge.ui.components.dropdown.DropDownItem
import biz.ohrae.challenge_repo.data.remote.ApiService
import biz.ohrae.challenge_repo.data.remote.NetworkResponse
import biz.ohrae.challenge_repo.model.FlowResult
import biz.ohrae.challenge_repo.model.account.BankCode
import biz.ohrae.challenge_repo.util.prefs.SharedPreference
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MyChallengeRepo @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val prefs: SharedPreference
) {
    suspend fun retrieveBankCodes(): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token.toString()
        val response = apiService.retrieveBankCodes(accessToken)

        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                if (isSuccess) {
                    val dataset = response.body.dataset
                    dataset?.let {
                        val listType = object : TypeToken<List<BankCode?>?>() {}.type
                        val bankList = gson.fromJson<List<BankCode>>(it.asJsonArray, listType)

                        val dropdownList = mutableListOf<DropDownItem>()
                        bankList.forEach { bank ->
                            dropdownList.add(DropDownItem(bank.name, bank.code))
                        }
                        return flow {
                            emit(FlowResult(dropdownList, null, null))
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

    suspend fun registerBankAccount(bankCode: String, accountNumber: String): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token.toString()
        val jsonObject = JsonObject()
        jsonObject.addProperty("bank_code", bankCode)
        jsonObject.addProperty("account_number", accountNumber)

        val response = apiService.registerBankAccount(accessToken, jsonObject)

        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                if (isSuccess) {
                    val dataset = response.body.dataset
                    dataset?.let {
                        return flow {
                            emit(FlowResult(true, null, null))
                        }
                    } ?: run {
                        return flow {
                            emit(FlowResult(null, "", "data is null"))
                        }
                    }
                } else {
                    return flow {
                        emit(FlowResult(false, response.body.code, response.body.message))
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

    suspend fun transferRewards(amount: Int): Flow<FlowResult> {
        val accessToken = prefs.getUserData()?.access_token.toString()
        val jsonObject = JsonObject()
        jsonObject.addProperty("amount", amount)

        val response = apiService.transferRewards(accessToken, jsonObject)

        when (response) {
            is NetworkResponse.Success -> {
                val isSuccess = response.body.success
                if (isSuccess) {
                    val dataset = response.body.dataset
                    dataset?.let {
                        return flow {
                            emit(FlowResult(true, null, null))
                        }
                    } ?: run {
                        return flow {
                            emit(FlowResult(null, "", "data is null"))
                        }
                    }
                } else {
                    return flow {
                        emit(FlowResult(false, response.body.code, response.body.message))
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
}