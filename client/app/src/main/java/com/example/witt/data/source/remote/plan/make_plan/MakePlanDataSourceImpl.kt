package com.example.witt.data.source.remote.plan.make_plan

import com.example.witt.data.api.PlanService
import com.example.witt.data.model.remote.plan.make_plan.request.MakePlanRequest
import com.example.witt.data.model.remote.plan.make_plan.response.MakePlanResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MakePlanDataSourceImpl @Inject constructor(
    private val planService: PlanService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): MakePlanDataSource {
    override suspend fun makePlan(makePlanRequest: MakePlanRequest): Result<MakePlanResponse> =
        withContext(coroutineDispatcher){
            runCatching {
                planService.makePlan(
                    request = makePlanRequest
                )
            }.onFailure { e ->
                e.printStackTrace()
            }
        }
}