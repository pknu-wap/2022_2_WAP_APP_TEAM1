package com.example.witt.domain.model.plan.get_plan

import com.example.witt.domain.model.plan.PlanStateModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class GetPlanListModel (
    val status : Boolean,
    val reason: String,
    val result: List<GetPlanListResultModel>
        )

data class GetPlanListResultModel(
    val TripId: Int,
    val StartDate: LocalDateTime,
    val EndDate: LocalDateTime,
    val Name: String,
    val Region: String
)

fun GetPlanListResultModel.toPlanStateModel() = PlanStateModel(
    TripId = TripId,
    StartDate = StartDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
    EndDate = EndDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
    Name = Name,
    Region = Region
)