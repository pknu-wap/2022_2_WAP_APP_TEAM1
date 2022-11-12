package com.example.witt.domain.repository

import com.example.witt.domain.model.plan.GetPlanListModel
import com.example.witt.domain.model.plan.GetPlanModel
import com.example.witt.domain.model.plan.MakePlanModel
import com.example.witt.domain.model.plan.MakePlanResponseModel

interface PlanRepository {

    suspend fun makePlan(makePlanModel: MakePlanModel): Result<MakePlanResponseModel>

    suspend fun getPlanList(): Result<List<GetPlanListModel>>

    suspend fun getPlan(planId: String): Result<GetPlanModel>

}