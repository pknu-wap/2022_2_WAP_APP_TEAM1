package com.example.witt.domain.use_case.date

import com.example.witt.di.DefaultDispatcher
import com.example.witt.domain.model.plan.DayPlanModel
import com.example.witt.domain.model.plan.get_plan.DetailPlanModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetDateDayUseCase @Inject constructor(
    @DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher
){
     suspend operator fun invoke(startDate: String, endDate: String, data: List<DetailPlanModel>) : List<DayPlanModel>
     = withContext(coroutineDispatcher){
        var sd = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        val ed = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"))

        val result : MutableList<DayPlanModel> = mutableListOf()

        var i = 1
        while(sd <= ed) {
            result.add(DayPlanModel("Day $i", sd.toString(), data.filter{it.Day == i}))
            sd = sd.plusDays(1)
            i += 1
        }
         result
    }
}