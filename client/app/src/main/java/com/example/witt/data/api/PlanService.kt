package com.example.witt.data.api

import com.example.witt.data.model.plan.make_plan.request.MakePlanRequest
import com.example.witt.data.model.plan.make_plan.response.MakePlanResponse
import com.example.witt.data.model.plan.remove_plan.RemovePlanResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT

interface PlanService {

    @PUT("/api/trip")
    suspend fun makePlan(
        @Body request: MakePlanRequest
    ):MakePlanResponse

    @DELETE("/api/trip")
    suspend fun removePlan(
        @Body tripId: Int
    ): RemovePlanResponse

}