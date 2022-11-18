package com.example.witt.data.model.plan.get_plan

import com.example.witt.domain.model.plan.get_plan.DetailPlanModel
import com.example.witt.domain.model.plan.get_plan.GetPlanModel
import com.example.witt.domain.model.plan.get_plan.PlanMemoModel

data class GetPlanResponse (
    val status: Boolean,
    val reason: String,
    val plans: List<DetailPlanResponse>
)

data class DetailPlanResponse(
    val PlanId : Int,
    val Day: Int,
    val OrderIndex: Int,
    val Memo: PlanMemoResponse
)

data class PlanMemoResponse(
    val Content : String,
    val Created_At: String,
    val Updated_At: String
)

fun GetPlanResponse.toGetPlanModel() = GetPlanModel(
    status = status,
    reason = reason,
    plans = plans.map{ it.toDetailPlanModel() }
)

fun DetailPlanResponse.toDetailPlanModel() = DetailPlanModel(
    PlanId = PlanId,
    Day = Day,
    OrderIndex = OrderIndex,
    Memo = Memo.toPlanMemoModel()
)

fun PlanMemoResponse.toPlanMemoModel() = PlanMemoModel(
    Content = Content,
    Created_At = Created_At,
    Updated_At = Updated_At
)