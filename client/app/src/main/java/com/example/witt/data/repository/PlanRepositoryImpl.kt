package com.example.witt.data.repository

import com.example.witt.data.mapper.toMakePlanResponseModel
import com.example.witt.data.model.plan.make_plan.request.MakePlanRequest
import com.example.witt.data.source.remote.plan.make_plan.MakePlanDataSource
import com.example.witt.domain.model.plan.MakePlanModel
import com.example.witt.domain.model.plan.MakePlanResponseModel
import com.example.witt.domain.repository.PlanRepository
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(
    private val makePlanDataSource: MakePlanDataSource
): PlanRepository{
    override suspend fun makePlan(makePlanModel: MakePlanModel): Result<MakePlanResponseModel> {
        return makePlanDataSource.makePlan(
            MakePlanRequest(
                StartDate = makePlanModel.StartDate,
                EndDate = makePlanModel.EndDate,
                Name = makePlanModel.Name,
                Region = makePlanModel.Region
            )
        ).mapCatching { response ->
            response.toMakePlanResponseModel()
        }
    }
}