package com.example.witt.domain.repository

import com.example.witt.domain.model.flight.SearchFlightModel
import com.example.witt.domain.model.flight.SearchFlightRequest

interface FlightRepository {

    suspend fun findFlight(searchFlightRequest: SearchFlightRequest) : Result<SearchFlightModel>

}