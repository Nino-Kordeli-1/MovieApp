package com.network.interceptor

import com.movieapp.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${BuildConfig.TMDB_TOKEN}"
            )
            .addHeader(
                "accept", "application/json"
            ).build()
        return chain.proceed(request)
    }
}