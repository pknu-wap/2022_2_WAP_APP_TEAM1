package com.example.witt.data.source.remote.plan.remove_plan

import com.example.witt.data.model.plan.remove_plan.RemovePlanResponse

interface RemovePlanDataSource {

    suspend fun removePlan(tripId: Int): Result<RemovePlanResponse>

}