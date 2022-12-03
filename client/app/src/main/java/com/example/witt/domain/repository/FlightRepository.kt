package com.example.witt.domain.repository

import com.example.witt.domain.model.remote.detail_plan.search.flight.AddFlightRequest
import com.example.witt.domain.model.remote.detail_plan.search.SearchFlightModel
import com.example.witt.domain.model.remote.detail_plan.search.SearchFlightRequest

interface FlightRepository {

    suspend fun findFlight(searchFlightRequest: SearchFlightRequest) : Result<SearchFlightModel>

    suspend fun addFlight(tripId:Int,addFlightRequest: AddFlightRequest) : Result<Boolean>

}