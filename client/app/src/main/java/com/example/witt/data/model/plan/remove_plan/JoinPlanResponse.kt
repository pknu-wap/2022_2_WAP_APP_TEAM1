package com.example.witt.data.model.plan.remove_plan

import com.example.witt.domain.model.plan.remove_plan.JoinPlanModel

data class JoinPlanResponse (
    val status: Boolean,
    val reason: String
        )

fun JoinPlanResponse.toJoinPlanModel() = JoinPlanModel(
    status = status,
    reason = reason
)