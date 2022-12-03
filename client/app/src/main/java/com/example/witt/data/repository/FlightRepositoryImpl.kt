package com.example.witt.data.repository

import android.content.Context
import com.example.witt.BuildConfig
import com.example.witt.data.api.DetailPlanService
import com.example.witt.data.api.interceptor.AuthInterceptor
import com.example.witt.data.model.search.flight.toAddFlightResponse
import com.example.witt.domain.model.flight.AddFlightRequest
import com.example.witt.domain.model.flight.SearchFlightModel
import com.example.witt.domain.model.flight.SearchFlightRequest
import com.example.witt.domain.repository.FlightRepository
import com.gun0912.tedpermission.provider.TedPermissionProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class FlightRepositoryImpl:FlightRepository {

    override suspend fun findFlight(searchFlightRequest: SearchFlightRequest): Result<SearchFlightModel> =
        withContext(Dispatchers.IO) {
            try {
                val retrofit = Retrofit.Builder()
//                    .client(OkHttpClient())
                    .baseUrl(BuildConfig.host_domain)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                val api = retrofit.create(DetailPlanService::class.java)
                val result = api.searchFlight(searchFlightRequest.flightDate,searchFlightRequest.airlineCode,searchFlightRequest.flightNum)
                return@withContext Result.success(result.toAddFlightResponse())
            } catch (e: Exception) {
                e.stackTrace
                Result.failure(e)
            }
        }

    private val authInterceptor = AuthInterceptor(TedPermissionProvider.context.getSharedPreferences("pref",
        Context.MODE_PRIVATE))
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
                val api = retrofit.create(DetailPlanService::class.java)
                val result = api.addFlight(tripId, addFlightRequest)
                return@withContext Result.success(result.status)
            } catch (e: Exception) {
                e.stackTrace
                Result.failure(e)
            }
        }
}