package biz.ohrae.challenge_repo.data.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object RetrofitSender {
    class HeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest: Request = chain.request()
            val newRequest = originalRequest.newBuilder()
                .build()
            return chain.proceed(newRequest)
        }
    }
}
