package com.example.witt.data.source.remote.plan.remove_plan

import com.example.witt.data.api.PlanService
import com.example.witt.data.model.plan.remove_plan.RemovePlanResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemovePlanDataSourceImpl @Inject constructor(
    private val service : PlanService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : RemovePlanDataSource{
    override suspend fun removePlan(tripId: Int): Result<RemovePlanResponse>
    = withContext(coroutineDispatcher){
        runCatching {
            service.removePlan(tripId)
        }.onFailure { e->
            e.printStackTrace()
        }
    }
}