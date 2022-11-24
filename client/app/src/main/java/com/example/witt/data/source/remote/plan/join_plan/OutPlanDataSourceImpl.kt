package com.example.witt.data.source.remote.plan.join_plan

import com.example.witt.data.api.PlanService
import com.example.witt.data.model.plan.join_plan.OutPlanResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OutPlanDataSourceImpl @Inject constructor(
    private val service : PlanService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): OutPlanDataSource{

    override suspend fun outPlan(tripId: Int): Result<OutPlanResponse>
    = withContext(coroutineDispatcher){
        runCatching {
            service.outPlan(tripId)
        }.onFailure { e->
            e.printStackTrace()
        }
    }
}