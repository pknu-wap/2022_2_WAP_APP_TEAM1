package com.example.witt.data.source.remote.plan.get_plan

import com.example.witt.data.api.PlanService
import com.example.witt.data.model.remote.plan.get_plan.GetPlanListResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPlanListDataSourceImpl @Inject constructor(
    private val getPlanService: PlanService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): GetPlanListDataSource {
    override suspend fun getPlanList(): Result<GetPlanListResponse>
    = withContext(coroutineDispatcher){
        runCatching {
            getPlanService.getPlanList()
        }.onFailure { error ->
            error.printStackTrace()
        }
    }
}