package com.example.witt.data.model.plan.remove_plan

import com.example.witt.domain.model.plan.remove_plan.RemovePlanModel

data class RemovePlanResponse (
    val status: Boolean,
    val reason: String
        )

fun RemovePlanResponse.toRemovePlanModel() = RemovePlanModel(
    status = status,
    reason = reason
)