package com.example.witt.data.source.remote.plan.remove_plan

import com.example.witt.data.model.plan.remove_plan.JoinPlanResponse

interface JoinPlanDataSource {

    suspend fun joinPlan(tripId: Int): Result<JoinPlanResponse>

}