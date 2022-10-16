package com.example.witt.data.api.interceptor

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authRequest = when (val accessToken = sharedPreferences.getString("accessToken", null)) {
            null -> {
                chain.request()
                    .newBuilder()
                    .build()
            }
            else -> {
                chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()
            }
        }
        return chain.proceed(authRequest)
    }
}