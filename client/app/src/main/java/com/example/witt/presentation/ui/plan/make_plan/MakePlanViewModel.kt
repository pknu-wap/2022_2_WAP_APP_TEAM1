package com.example.witt.presentation.ui.plan.make_plan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MakePlanViewModel: ViewModel() {

    private val planStartDate : MutableLiveData<String> = MutableLiveData()

    private val planEndDate : MutableLiveData<String> = MutableLiveData()

    private val planDestination : MutableLiveData<String> = MutableLiveData()

    fun onEvent(event: MakePlanEvent){
        when(event){
            is MakePlanEvent.SubmitPlanDate ->{
                planStartDate.value = event.startDate
                planEndDate.value = event.endDate
            }
            is MakePlanEvent.SubmitDestination ->{
                planDestination.value = event.destination
            }
        }
    }

}