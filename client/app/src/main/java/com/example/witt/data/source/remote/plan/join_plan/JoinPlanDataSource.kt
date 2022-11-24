package com.example.witt.data.source.remote.plan.join_plan

import com.example.witt.data.model.plan.join_plan.JoinPlanResponse

interface JoinPlanDataSource {

    suspend fun joinPlan(tripId: Int): Result<JoinPlanResponse>

}