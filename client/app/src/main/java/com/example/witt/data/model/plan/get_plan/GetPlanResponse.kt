package com.example.witt.data.model.plan.get_plan

import com.example.witt.domain.model.plan.get_plan.DetailPlanModel
import com.example.witt.domain.model.plan.get_plan.GetPlanModel
import com.example.witt.domain.model.plan.get_plan.PlanMemoModel

data class GetPlanResponse (
    val message: String,
    val reason: String,
    val planDetail: List<DetailPlanResponse>
)
data class DetailPlanResponse(
    val PlanDetailId : String,
    val OrderIndex: String,
    val PlanMemos: List<PlanMemoResponse>
)
data class PlanMemoResponse(
    val Content : String
)

fun GetPlanResponse.toGetPlanModel() = GetPlanModel(
    message = message,
    reason = reason,
    planDetail = planDetail.map{ it.toDetailPlanModel() }
)

fun DetailPlanResponse.toDetailPlanModel() = DetailPlanModel(
    PlanDetailId = PlanDetailId,
    OrderIndex = OrderIndex,
    PlanMemos = PlanMemos.map { it.toPlanMemoModel() }
)

fun PlanMemoResponse.toPlanMemoModel() = PlanMemoModel(
    Content = Content
)