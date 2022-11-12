package com.example.witt.presentation.ui.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.model.plan.PlanDetailModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanViewModel : ViewModel(){

    private var _planDetail :MutableStateFlow<PlanDetailModel> = MutableStateFlow(PlanDetailModel("","","","",""))
    val planDetail : StateFlow<PlanDetailModel> get() = _planDetail

    //get plan
    // 1번 만들어진 일정에서 클릭

    // 2번 일정을 만들 때

    fun setPlanDetail(planModel: PlanDetailModel){
        viewModelScope.launch {
            _planDetail.value = planModel
        }
    }
}