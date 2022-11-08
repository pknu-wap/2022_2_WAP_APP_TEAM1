package com.example.witt.presentation.ui.plan.make_plan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MakePlanViewModel: ViewModel() {

    companion object{
        const val SEOUL = 1
        const val BUSAN = 2
        const val JEJU = 3
        const val JEONJU = 4
        const val GANGREUNG = 5
        const val DAEJEON = 6
        const val ETC = 7
    }

    private val planStartDate : MutableLiveData<String> = MutableLiveData()
    private val planEndDate : MutableLiveData<String> = MutableLiveData()

    val planDestination : MutableLiveData<String> = MutableLiveData()
    val inputPlanId: MutableLiveData<String> = MutableLiveData()

    fun onButtonEvent(destination: Int){
        when(destination){
            SEOUL ->{
                planDestination.value = "서울/경기로 목적지 설정"
            }
            BUSAN ->{
                planDestination.value = "부산/경상으로 목적지 설정"
            }
            JEJU ->{
                planDestination.value = "제주/우도로 목적지 설정"
            }
            JEONJU ->{
                planDestination.value = "전주/전라로 목적지 설정"
            }
            GANGREUNG ->{
                planDestination.value = "강릉/강원으로 목적지 설정"
            }
            DAEJEON ->{
                planDestination.value = "대전/충청으로 목적지 설정"
            }
            ETC ->{
                planDestination.value = "기타 목적지 설정"
            }
        }
    }

    fun onEvent(event: MakePlanEvent){
        when(event){
            is MakePlanEvent.SubmitPlanDate ->{
                planStartDate.value = event.startDate
                planEndDate.value = event.endDate
            }
        }
    }
}
