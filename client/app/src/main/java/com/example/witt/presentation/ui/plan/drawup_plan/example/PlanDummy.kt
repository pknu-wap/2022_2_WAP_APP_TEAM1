package com.example.witt.presentation.ui.plan.drawup_plan.example

import com.example.witt.domain.model.plan.DatePlanModel
import com.example.witt.domain.model.plan.TimePlanModel

class PlanDummy {
    companion object {

        fun getTimePlan(): ArrayList<TimePlanModel> {
            val timePlan = ArrayList<TimePlanModel>()
            timePlan.add(
                TimePlanModel(
                    "우도 다녀오기"))
            timePlan.add(
                TimePlanModel(
                    "제주도 레전드 고기국수 방문"
                ))
            timePlan.add(
                TimePlanModel(
                    "제주도 스쿠버 다이빙 하기"
                )
            )
            return timePlan
        }

        fun getDatePlan(): ArrayList<DatePlanModel> {
            val datePlan = ArrayList<DatePlanModel>()
            datePlan.add(
                DatePlanModel(
                    "DAY 1",
                    "2022.11.21"
                ))
            datePlan.add(
                DatePlanModel(
                    "DAY 2",
                    "2022.11.22"
                ))
            datePlan.add(
                DatePlanModel(
                    "DAY 3",
                    "2022.11.23"
                )
            )
            return datePlan
        }
    }
}