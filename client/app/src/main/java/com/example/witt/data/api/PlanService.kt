package com.example.witt.data.api

import com.example.witt.data.model.plan.get_plan.GetPlanListResponse
import com.example.witt.data.model.plan.get_plan.GetPlanResponse
import com.example.witt.data.model.plan.join_plan.JoinPlanResponse
import com.example.witt.data.model.plan.join_plan.OutPlanResponse
import com.example.witt.data.model.plan.make_plan.request.MakePlanRequest
import com.example.witt.data.model.plan.make_plan.response.MakePlanResponse
import retrofit2.http.*

interface PlanService {

    @GET("/api/trip")
    suspend fun getPlanList(
    ): GetPlanListResponse

    @GET("/api/trip/{planId}")
    suspend fun getPlan(
        @Path("planId") planId: Int
    ): GetPlanResponse

    @PUT("/api/trip")
    suspend fun makePlan(
        @Body request: MakePlanRequest
    ):MakePlanResponse

    @DELETE("/api/trip/{tripId}")
    suspend fun outPlan(
        @Path ("tripId") tripId: Int
    ): OutPlanResponse

    @PUT("/api/trip/{tripId}/participants")
    suspend fun joinPlan(
        @Path ("tripId") tripId: Int
    ): JoinPlanResponse

}