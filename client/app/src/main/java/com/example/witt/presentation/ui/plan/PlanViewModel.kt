package com.example.witt.presentation.ui.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.model.plan.PlanStateModel
import com.example.witt.domain.repository.PlanRepository
import com.example.witt.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val repository: PlanRepository
): ViewModel(){

    //dataBinding
    private val _planState : MutableLiveData<PlanStateModel> = MutableLiveData()
    val planState : LiveData<PlanStateModel> get() = _planState

    //tripId 전달
    private val _joinPlanUiEvent : MutableSharedFlow<UiEvent<Int>> = MutableSharedFlow()
    val joinPlanUiEvent : SharedFlow<UiEvent<Int>> get() = _joinPlanUiEvent

    fun setPlanState(planItem: PlanStateModel){
        viewModelScope.launch {
            _planState.value = planItem
        }
    }

    fun joinPlan(tripId: Int){
        viewModelScope.launch {
            repository.joinPlan(tripId).mapCatching {
                if(it.status){
                    //drawUpFragment에서 호출
                    //받으면 전환 및 getDetailPlan
                    _joinPlanUiEvent.emit(UiEvent.Success(tripId))
                }else{
                    _joinPlanUiEvent.emit(UiEvent.Failure("일정에 참가하지 못하였습니다."))
                }
            }
        }
    }
}