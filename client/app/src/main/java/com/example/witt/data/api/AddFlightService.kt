package com.example.witt.data.api

import com.example.witt.data.model.search.flight.AddFlightResponse
import com.example.witt.domain.model.flight.AddFlightRequest
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddFlightService {
    @PUT("/api/trip/{tripId}/plan/flight")
    suspend fun addFlight(
        @Path("tripId") tripId:Int,
        @Body request: AddFlightRequest
    ): AddFlightResponse
}

