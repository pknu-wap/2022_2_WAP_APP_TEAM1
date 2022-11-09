package com.example.witt.data.api

import com.example.witt.data.model.plan.make_plan.request.MakePlanRequest
import com.example.witt.data.model.plan.make_plan.response.MakePlanResponse
import retrofit2.http.Body
import retrofit2.http.PUT

interface MakePlanService {

    @PUT("/api/plan")
    suspend fun makePlan(
        @Body request: MakePlanRequest
    ):MakePlanResponse

}