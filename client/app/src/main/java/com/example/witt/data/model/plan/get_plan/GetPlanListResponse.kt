package com.example.witt.data.model.plan.get_plan

import com.example.witt.domain.model.plan.get_plan.GetPlanListModel
import com.example.witt.domain.model.plan.get_plan.GetPlanListResultModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class GetPlanListResponse (
   val status : Boolean,
   val reason: String,
   val result: List<GetPlanListResultResponse>
)

data class GetPlanListResultResponse (
   val TripId: Int,
   val Name: String,
   val StartDate: String,
   val EndDate: String,
   val Region: String
   )

//todo 서버시간 to string
fun GetPlanListResponse.toGetPlanListModel() = GetPlanListModel(
   status = status,
   reason = reason,
   result = result.map{ it.toGetPlanListResultModel()}
)

fun GetPlanListResultResponse.toGetPlanListResultModel() = GetPlanListResultModel(
   TripId = TripId,
   StartDate = LocalDateTime.parse(StartDate, DateTimeFormatter.ISO_DATE_TIME),
   EndDate = LocalDateTime.parse(EndDate, DateTimeFormatter.ISO_DATE_TIME),
   Name = Name,
   Region = Region
)
