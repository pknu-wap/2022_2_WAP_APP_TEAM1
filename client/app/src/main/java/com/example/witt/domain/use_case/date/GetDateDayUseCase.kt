package com.example.witt.domain.use_case.date

import com.example.witt.domain.model.plan.DayPlanModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetDateDayUseCase {

    operator fun invoke(startDate: String, endDate: String) : List<DayPlanModel>{

        var sd = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        val ed = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"))

        val result : MutableList<DayPlanModel> = mutableListOf()

        var i = 1
        while(sd <= ed) {
            result.add(DayPlanModel("day $i", sd.toString()))
            sd = sd.plusDays(1)
            i += 1
        }

        return result
    }
}