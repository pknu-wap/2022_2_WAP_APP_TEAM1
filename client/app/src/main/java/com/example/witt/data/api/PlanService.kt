package com.example.witt.data.api

import com.example.witt.data.model.plan.join_plan.JoinPlanResponse
import com.example.witt.data.model.plan.join_plan.OutPlanResponse
import com.example.witt.data.model.plan.make_plan.request.MakePlanRequest
import com.example.witt.data.model.plan.make_plan.response.MakePlanResponse
import com.example.witt.data.model.plan.remove_plan.RemovePlanResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

interface PlanService {

    @PUT("/api/trip")
    suspend fun makePlan(
        @Body request: MakePlanRequest
    ):MakePlanResponse

    @PUT("/api/trip/{tripId}/participants")
    suspend fun joinPlan(
        @Path ("tripId") tripId: Int
    ): JoinPlanResponse

    @DELETE("/api/trip/{tripId}/participants")
    suspend fun outPlan(
        @Path ("tripId") tripId: Int
    ): OutPlanResponse

}