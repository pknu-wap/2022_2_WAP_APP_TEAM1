package com.example.witt.domain.model.use_case.plan

data class PlanStateModel(
    val TripId: Int,
    val StartDate: String,
    val EndDate: String,
    val Name: String,
    val Region: String
)
