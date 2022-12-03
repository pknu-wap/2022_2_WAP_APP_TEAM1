package com.example.witt.data.model.remote.plan.out_plan

import com.example.witt.domain.model.remote.plan.out_plan.OutPlanModel


data class OutPlanResponse (
    val status: Boolean,
    val reason: String
    )

fun OutPlanResponse.toOutPlanModel() = OutPlanModel(
    status = status,
    reason = reason
)