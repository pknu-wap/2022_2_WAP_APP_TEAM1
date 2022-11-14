package com.example.witt.data.api

import com.example.witt.data.model.plan.get_plan.GetPlanListResponse
import com.example.witt.data.model.plan.get_plan.GetPlanResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GetPlanService {

    @GET("/api/trip")
    suspend fun getPlanList(
    ): List<GetPlanListResponse>

    @GET("/api/trip/{planId}")
    suspend fun getPlan(
        @Path("planId") planId: String
    ): GetPlanResponse

}