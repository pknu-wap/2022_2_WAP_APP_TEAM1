package com.example.witt.domain.repository

import com.example.witt.domain.model.flight.AddFlightRequest

interface AddFlightRepository {
    suspend fun addFlight(tripId:Int,addFlightRequest: AddFlightRequest) : Result<Boolean>
}