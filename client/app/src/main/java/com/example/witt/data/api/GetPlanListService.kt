package com.example.witt.data.api

import com.example.witt.data.model.plan.get_plan.GetPlanListResponse
import retrofit2.http.GET

interface GetPlanListService {

    @GET("/api/plan")
    suspend fun getPlanList(
    ): List<GetPlanListResponse>

}