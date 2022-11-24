package com.example.witt.data.model.plan.join_plan

import com.example.witt.domain.model.plan.join_plan.OutPlanModel


data class OutPlanResponse (
    val status: Boolean,
    val reason: String
    )

fun OutPlanResponse.toOutPlanModel() = OutPlanModel(
    status = status,
    reason = reason
)