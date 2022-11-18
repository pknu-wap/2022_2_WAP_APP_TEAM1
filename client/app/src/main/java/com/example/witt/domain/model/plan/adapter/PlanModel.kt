package com.example.witt.domain.model.plan.adapter

import com.example.witt.domain.model.plan.get_plan.DetailPlanModel

data class PlanModel (
    val day: String,
    val date: String,
    val memo: List<DetailPlanModel>
        )