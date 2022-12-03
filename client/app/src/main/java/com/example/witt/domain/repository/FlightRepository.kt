package com.example.witt.domain.repository

import com.example.witt.domain.model.flight.AddFlightRequest
import com.example.witt.domain.model.flight.SearchFlightModel
import com.example.witt.domain.model.flight.SearchFlightRequest

interface FlightRepository {

    suspend fun findFlight(searchFlightRequest: SearchFlightRequest) : Result<SearchFlightModel>

    suspend fun addFlight(tripId:Int,addFlightRequest: AddFlightRequest) : Result<Boolean>

}