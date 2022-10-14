package biz.ohrae.challenge_repo.data.remote

import com.google.firebase.crashlytics.FirebaseCrashlytics
import okhttp3.Request
import timber.log.Timber
import java.io.IOException

sealed class NetworkResponse<out T : Any, out U : Any> {
    var errorMessage: String? = null

    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>() {
        init {
            try {
                when(body) {
                    is Result -> {
                        val result = body as Result
                        if(!result.success) {
//                            App.INSTANCE.showToast(result.code.toString(), result.message.toString())
                        }
                    }
                    is Result2 -> {
                        val result = body as Result2
                        if(!result.success) {
//                            App.INSTANCE.showToast(result.code.toString(), result.message.toString())
                        }
                    }
                }
            }
            catch (ignore: Exception) {
            }
        }
    }

    /**
     * Failure response with body
     */
    data class ApiError<U : Any>(val body: U, val code: Int) : NetworkResponse<Nothing, U>()

    /**
     * Network error
     */
    data class NetworkError(val request: Request?, val error: IOException) : NetworkResponse<Nothing, Nothing>() {
        init {
            errorMessage = error.message.toString()
            Timber.e( "NetworkError : ${error.message}")
//            reportNetworkError(request!!, error)
        }
    }

    /**
     * For example, json parsing error
     */
    data class UnknownError(val request: Request, val error: Throwable?) : NetworkResponse<Nothing, Nothing>() {
        init {
            errorMessage = error?.message.toString()
            Timber.e("UnknownError : ${error?.message.toString()}")
//            reportNetworkError(request, error)
        }
    }

    fun reportNetworkError(request: Request, error: Throwable?) {
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCustomKey("url", request.url.toString())
        crashlytics.setCustomKey("body", request.body.toString())
        if(error != null) {
            crashlytics.recordException(error)
        }
        else {
            crashlytics.recordException(Throwable("UnknownError"))
        }
    }
}
