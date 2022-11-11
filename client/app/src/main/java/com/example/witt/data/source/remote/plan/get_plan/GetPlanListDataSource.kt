package com.example.witt.data.source.remote.plan.get_plan

import com.example.witt.data.model.plan.get_plan.GetPlanListResponse

interface GetPlanListDataSource {
    suspend fun getPlanList(): Result<List<GetPlanListResponse>>
}