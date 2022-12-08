package com.example.witt.domain.model.remote.plan.get_plan

import com.example.witt.domain.model.use_case.plan.PlanStateModel
import java.time.format.DateTimeFormatter

data class GetPlanListModel(
    val status: Boolean,
    val reason: String,
    val result: List<GetPlanListResultModel>
)

data class GetPlanListResultModel(
    val TripId: Int,
    val StartDate: String,
    val EndDate: String,
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
