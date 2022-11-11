package com.example.witt.domain.model.plan

import java.time.LocalDateTime

data class GetPlanModel (
    val PlanId: String,
    val StartDate: LocalDateTime,
    val EndDate: LocalDateTime,
    val Name: String,
    val Region: String
        )