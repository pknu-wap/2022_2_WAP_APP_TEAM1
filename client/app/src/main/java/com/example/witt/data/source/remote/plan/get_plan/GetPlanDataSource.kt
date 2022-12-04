package com.example.witt.data.source.remote.plan.get_plan

import com.example.witt.data.model.remote.plan.get_plan.GetPlanResponse

interface GetPlanDataSource {
    suspend fun getPlan(planId: Int): Result<GetPlanResponse>
}
