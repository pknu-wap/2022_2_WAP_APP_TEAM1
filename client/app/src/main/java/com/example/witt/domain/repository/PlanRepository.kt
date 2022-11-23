package com.example.witt.domain.repository

import com.example.witt.domain.model.plan.get_plan.GetPlanListModel
import com.example.witt.domain.model.plan.get_plan.GetPlanModel
import com.example.witt.domain.model.plan.make_plan.MakePlanModel
import com.example.witt.domain.model.plan.make_plan.MakePlanResponseModel
import com.example.witt.domain.model.plan.remove_plan.JoinPlanModel
import com.example.witt.domain.model.plan.remove_plan.RemovePlanModel

interface PlanRepository {

    suspend fun makePlan(makePlanModel: MakePlanModel): Result<MakePlanResponseModel>

    suspend fun getPlanList(): Result<GetPlanListModel>

    suspend fun getPlan(planId: Int): Result<GetPlanModel>

    suspend fun removePlan(tripId: Int): Result<RemovePlanModel>

    suspend fun joinPlan(tripId: Int): Result<JoinPlanModel>

}