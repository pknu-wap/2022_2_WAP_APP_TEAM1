package com.example.witt.data.api

import com.example.witt.data.model.search.flight.SearchFlightResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightSearchService {
    @GET("/api/flight/flightNumber")
    suspend fun searchFlight(
        @Query("flightDate") flightDate: String,
        @Query("airlineCode") airlineCode:String,
        @Query("flightNum") flightNum:String
    ): SearchFlightResponse
}

