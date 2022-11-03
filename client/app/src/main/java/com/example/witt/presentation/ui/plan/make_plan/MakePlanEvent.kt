package com.example.witt.presentation.ui.plan.make_plan

sealed class MakePlanEvent {

    data class SubmitPlanDate(val startDate: String, val endDate: String): MakePlanEvent()

}