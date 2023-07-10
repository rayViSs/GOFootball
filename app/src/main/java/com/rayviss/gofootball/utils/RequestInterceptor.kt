package com.rayviss.gofootball.utils

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("username_api", Constants.USERNAME_API)
            .addHeader("Authorization", Constants.TOKEN_API)
            .build()

        return chain.proceed(request)
    }
}

