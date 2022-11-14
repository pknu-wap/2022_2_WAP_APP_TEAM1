package com.example.witt.domain.model.plan.get_plan

data class GetPlanModel (
    val message: String,
    val reason: String,
    val plans: List<DetailPlanModel>
)
data class DetailPlanModel (
    val PlanId : Int,
    val Day: Int,
    val OrderIndex: Int,
    val Memo: List<PlanMemoModel>
    )

data class PlanMemoModel (
    val Content : String,
    val Created_At: String,
    val Updated_At: String
    )
