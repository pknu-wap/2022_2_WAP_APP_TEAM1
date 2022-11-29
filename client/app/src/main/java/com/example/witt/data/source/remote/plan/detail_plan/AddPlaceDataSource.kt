package com.example.witt.data.source.remote.plan.detail_plan

import com.example.witt.data.model.plan.detail_plan.request.AddPlaceRequest
import com.example.witt.data.model.plan.detail_plan.response.AddPlaceResponse

interface AddPlaceDataSource {

    suspend fun addPlace(tripId: Int, dayId: Int, request: AddPlaceRequest): Result<AddPlaceResponse>
}