package com.example.witt.presentation.ui.plan.drawup_plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.model.plan.adapter.PlanModel
import com.example.witt.domain.model.plan.PlanStateModel
import com.example.witt.domain.repository.PlanRepository
import com.example.witt.domain.use_case.plan.ConvertDetailPlanUseCase
import com.example.witt.presentation.ui.UiEvent
import com.example.witt.presentation.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawUpViewModel @Inject constructor(
    private val planRepository: PlanRepository,
    private val convertDetailPlanUseCase: ConvertDetailPlanUseCase
) : ViewModel() {

    private val _drawUpPlanEvent = MutableSharedFlow<UiEvent<Unit>>()
    val drawUpPlanEvent : SharedFlow<UiEvent<Unit>> get() = _drawUpPlanEvent

    private val _drawUpPlanData : MutableStateFlow<UiState<List<PlanModel>>> = MutableStateFlow(UiState.Init)
    val drawUpPlanData : StateFlow<UiState<List<PlanModel>>> get() = _drawUpPlanData

    fun getDetailPlan(plan: PlanStateModel){
        viewModelScope.launch {
            planRepository.getPlan(plan.TripId).mapCatching { response ->
                if(response.status){
                    _drawUpPlanData.emit(UiState.Success(
                        convertDetailPlanUseCase(plan.StartDate, plan.EndDate, response.plans)
                    ))
                }else{
                    _drawUpPlanEvent.emit(UiEvent.Failure(response.reason))
                }
            }
        }
    }
}