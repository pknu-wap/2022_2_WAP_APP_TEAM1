package com.example.witt.data.model.plan.get_plan

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.witt.domain.model.plan.GetPlanModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class GetPlanListResponse (
   val PlanId: String,
   val StartDate: String,
   val EndDate: String,
   val Name: String,
   val Region: String
   )
//todo 서버시간 to string
fun GetPlanListResponse.toGetPlanModel() = GetPlanModel(
   PlanId = PlanId,
   StartDate = LocalDateTime.parse(StartDate, DateTimeFormatter.ISO_DATE_TIME),
   EndDate = LocalDateTime.parse(EndDate, DateTimeFormatter.ISO_DATE_TIME),
   Name = Name,
   Region = Region
)
