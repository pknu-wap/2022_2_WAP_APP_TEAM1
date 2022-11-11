package com.example.witt.domain.repository

import com.example.witt.domain.model.plan.GetPlanModel
import com.example.witt.domain.model.plan.MakePlanModel
import com.example.witt.domain.model.plan.MakePlanResponseModel
import kotlinx.coroutines.flow.Flow

interface PlanRepository {

    suspend fun makePlan(makePlanModel: MakePlanModel): Result<MakePlanResponseModel>

    suspend fun getPlan(): Result<List<GetPlanModel>>

}