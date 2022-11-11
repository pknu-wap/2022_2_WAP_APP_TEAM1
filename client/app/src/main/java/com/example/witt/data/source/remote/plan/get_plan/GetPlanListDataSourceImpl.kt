package com.example.witt.data.source.remote.plan.get_plan

import com.example.witt.data.api.GetPlanListService
import com.example.witt.data.model.plan.get_plan.GetPlanListResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPlanListDataSourceImpl @Inject constructor(
    private val getPlanListService: GetPlanListService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): GetPlanListDataSource {
    override suspend fun getPlanList(): Result<List<GetPlanListResponse>>
    = withContext(coroutineDispatcher){
        runCatching {
            getPlanListService.getPlanList()
        }.onFailure { error ->
            error.message
        }
    }
}