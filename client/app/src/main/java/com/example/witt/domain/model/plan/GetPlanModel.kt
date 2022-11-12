package com.example.witt.domain.model.plan

data class GetPlanModel (
    val message: String,
    val reason: String,
    val planDetail: List<DetailPlanModel>
)
data class DetailPlanModel (
    val PlanDetailId : String,
    val OrderIndex: String,
    val PlanMemos: List<PlanMemoModel>
)
data class PlanMemoModel (
    val Content : String
)

