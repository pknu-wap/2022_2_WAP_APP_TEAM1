package com.example.witt.data.repository

import com.example.witt.BuildConfig
import com.example.witt.data.api.FlightSearchService
import com.example.witt.data.model.search.flight.toAddFlightResponse
import com.example.witt.domain.model.flight.SearchFlightModel
import com.example.witt.domain.model.flight.SearchFlightRequest
import com.example.witt.domain.repository.FlightRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
                val api = retrofit.create(FlightSearchService::class.java)
                val result = api.searchFlight(searchFlightRequest.flightDate,searchFlightRequest.airlineCode,searchFlightRequest.flightNum)
                return@withContext Result.success(result.toAddFlightResponse())
            } catch (e: Exception) {
                e.stackTrace
                Result.failure(e)
            }
        }
}