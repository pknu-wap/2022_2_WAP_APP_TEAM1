package com.example.witt.domain.repository

import com.example.witt.domain.model.plan.MakePlanModel
import com.example.witt.domain.model.plan.MakePlanResponseModel

interface PlanRepository {

    suspend fun makePlan(makePlanModel: MakePlanModel): Result<MakePlanResponseModel>

}