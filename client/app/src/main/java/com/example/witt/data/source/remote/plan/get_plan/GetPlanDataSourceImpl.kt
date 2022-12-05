package com.example.witt.data.source.remote.plan.get_plan

import com.example.witt.data.api.PlanService
import com.example.witt.data.model.remote.plan.get_plan.GetPlanResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPlanDataSourceImpl @Inject constructor(
    private val getPlanService: PlanService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : GetPlanDataSource {
    override suspend fun getPlan(planId: Int): Result<GetPlanResponse> =
        withContext(coroutineDispatcher) {
            runCatching {
                getPlanService.getPlan(planId)
            }.onFailure { e ->
                e.printStackTrace()
            }
        }
}
