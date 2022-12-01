package com.example.witt.data.repository

import android.content.Context.MODE_PRIVATE
import com.example.witt.BuildConfig
import com.example.witt.data.api.AddFlightService
import com.example.witt.data.api.interceptor.AuthInterceptor
import com.example.witt.domain.model.flight.AddFlightRequest
import com.example.witt.domain.repository.AddFlightRepository
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class AddFlightRepositoryImpl : AddFlightRepository {
    private val authInterceptor = AuthInterceptor(context.getSharedPreferences("pref",MODE_PRIVATE))
    override suspend fun addFlight(tripId:Int, addFlightRequest: AddFlightRequest): Result<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val okHttpClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    .addInterceptor(authInterceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BuildConfig.host_domain)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                val api = retrofit.create(AddFlightService::class.java)
                val result = api.addFlight(tripId, addFlightRequest)
                return@withContext Result.success(result.status)
            } catch (e: Exception) {
                e.stackTrace
                Result.failure(e)
            }
        }
}