package com.example.witt.data.source.remote.plan.remove_plan

import com.example.witt.data.api.PlanService
import com.example.witt.data.model.plan.remove_plan.JoinPlanResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JoinPlanDataSourceImpl @Inject constructor(
    private val service : PlanService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): JoinPlanDataSource{
    override suspend fun joinPlan(tripId: Int): Result<JoinPlanResponse>
    = withContext(coroutineDispatcher){
        runCatching {
            service.joinPlan(tripId)
        }.onFailure { e->
            e.printStackTrace()
        }
    }
}