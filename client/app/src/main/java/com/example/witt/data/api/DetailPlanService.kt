package com.example.witt.data.api

import com.example.witt.data.model.plan.detail_plan.request.AddPlaceRequest
import com.example.witt.data.model.plan.detail_plan.response.AddPlaceResponse
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface DetailPlanService {

    @PUT("/api/trip/{tripId}/plan/days/{dayId}/place")
    suspend fun addPlace(
        @Path ("tripId") tripId: Int,
        @Path ("dayId") dayId: Int,
        @Body request: AddPlaceRequest
    ): AddPlaceResponse

}