package com.example.witt.data.model.remote.plan.join_plan

import com.example.witt.domain.model.remote.plan.join_plan.JoinPlanModel

data class JoinPlanResponse(
    val status: Boolean,
    val reason: String
)

fun JoinPlanResponse.toJoinPlanModel() = JoinPlanModel(
    status = status,
    reason = reason
)
