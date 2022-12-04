package com.example.witt.domain.repository

import com.example.witt.domain.model.remote.detail_plan.search.SearchFlightModel
import com.example.witt.domain.model.remote.detail_plan.search.SearchFlightRequest
import com.example.witt.domain.model.remote.detail_plan.search.flight.AddFlightRequestModel

interface FlightRepository {

    suspend fun findFlight(searchFlightRequest: SearchFlightRequest): Result<SearchFlightModel>

    suspend fun addFlight(tripId: Int, addFlightRequest: AddFlightRequestModel): Result<Boolean>
}
