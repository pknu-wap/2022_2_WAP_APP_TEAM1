package com.example.witt.domain.repository

import com.example.witt.domain.model.remote.plan.get_plan.GetPlanListModel
import com.example.witt.domain.model.remote.plan.get_plan.GetPlanModel
import com.example.witt.domain.model.remote.plan.join_plan.JoinPlanModel
import com.example.witt.domain.model.remote.plan.make_plan.MakePlanModel
import com.example.witt.domain.model.remote.plan.make_plan.MakePlanResponseModel
import com.example.witt.domain.model.remote.plan.out_plan.OutPlanModel

interface PlanRepository {

    suspend fun makePlan(makePlanModel: MakePlanModel): Result<MakePlanResponseModel>

    suspend fun getPlanList(): Result<GetPlanListModel>

    suspend fun getPlan(planId: Int): Result<GetPlanModel>

    suspend fun joinPlan(tripId: Int): Result<JoinPlanModel>

    suspend fun outPlan(tripId: Int): Result<OutPlanModel>
}
