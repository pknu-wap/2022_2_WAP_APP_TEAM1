package com.example.witt.data.model.remote.plan.get_plan

import com.example.witt.domain.model.remote.plan.get_plan.GetPlanListModel
import com.example.witt.domain.model.remote.plan.get_plan.GetPlanListResultModel
import com.example.witt.utils.convertIsoToDate

data class GetPlanListResponse(
    val status: Boolean,
    val reason: String,
    val result: List<GetPlanListResultResponse>
)

data class GetPlanListResultResponse(
    val TripId: Int,
    val Name: String,
    val StartDate: String,
    val EndDate: String,
    val Region: String
)

// todo 서버시간 to string
fun GetPlanListResponse.toGetPlanListModel() = GetPlanListModel(
    status = status,
    reason = reason,
    result = result.map { it.toGetPlanListResultModel() }
)

fun GetPlanListResultResponse.toGetPlanListResultModel() = GetPlanListResultModel(
    TripId = TripId,
    StartDate = StartDate.convertIsoToDate(),
    EndDate = EndDate.convertIsoToDate(),
    Name = Name,
    Region = Region
)
