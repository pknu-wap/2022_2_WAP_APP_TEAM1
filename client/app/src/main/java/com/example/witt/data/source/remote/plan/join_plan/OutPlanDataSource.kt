package com.example.witt.data.source.remote.plan.join_plan

import com.example.witt.data.model.remote.plan.out_plan.OutPlanResponse

interface OutPlanDataSource {

    suspend fun outPlan(tripId: Int): Result<OutPlanResponse>
}
