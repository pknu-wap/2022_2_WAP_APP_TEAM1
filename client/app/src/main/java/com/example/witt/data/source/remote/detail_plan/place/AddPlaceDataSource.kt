package com.example.witt.data.source.remote.detail_plan.place

import com.example.witt.data.model.remote.detail_plan.place.request.AddPlaceRequest
import com.example.witt.data.model.remote.detail_plan.place.response.AddPlaceResponse

interface AddPlaceDataSource {

    suspend fun addPlace(tripId: Int, dayId: Int, request: AddPlaceRequest): Result<AddPlaceResponse>
}