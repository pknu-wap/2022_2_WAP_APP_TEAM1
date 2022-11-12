package com.example.witt.data.model.plan.get_plan

data class GetPlanResponse (
    val message: String,
    val reason: String,
    val planDetail: List<DetailPlanResponse>
)
data class DetailPlanResponse(
    val PlanDetailId : String,
    val OrderIndex: String,
    val PlanMemos: List<PlanMemo>
)
data class PlanMemo(
    val Content : String
)
