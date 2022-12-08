package com.example.witt.domain.model.use_case.plan

import com.example.witt.domain.model.remote.plan.get_plan.DetailPlanModel

data class PlanModel(
    val day: String,
    val date: String,
    val detailPlan: List<DetailPlanModel>?
)
