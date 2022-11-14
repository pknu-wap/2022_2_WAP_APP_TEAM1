package com.example.witt.presentation.ui.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.model.plan.PlanStateModel
import kotlinx.coroutines.launch

class PlanViewModel : ViewModel(){

    //dataBinding
    private var _planState : MutableLiveData<PlanStateModel> = MutableLiveData()
    val planState : LiveData<PlanStateModel> get() = _planState

    fun setPlanState(planItem: PlanStateModel){
        viewModelScope.launch {
            _planState.value = planItem
        }
    }
}