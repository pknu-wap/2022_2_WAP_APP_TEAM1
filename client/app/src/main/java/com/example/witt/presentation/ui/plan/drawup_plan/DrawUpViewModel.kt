package com.example.witt.presentation.ui.plan.drawup_plan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.model.plan.adapter.PlanModel
import com.example.witt.domain.model.plan.PlanStateModel
import com.example.witt.domain.model.plan.get_plan.PlanDataModel
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

    private val planStateData : MutableLiveData<PlanStateModel> = MutableLiveData()

    private val _drawUpPlanEvent = MutableSharedFlow<UiEvent<Unit>>()
    val drawUpPlanEvent : SharedFlow<UiEvent<Unit>> get() = _drawUpPlanEvent

    private val _drawUpPlanData : MutableStateFlow<UiState<List<PlanModel>>> = MutableStateFlow(UiState.Init)
    val drawUpPlanData : StateFlow<UiState<List<PlanModel>>> get() = _drawUpPlanData

    private val _planData: MutableStateFlow<UiState<PlanDataModel>> = MutableStateFlow(UiState.Init)
    val planData : StateFlow<UiState<PlanDataModel>> get() = _planData

    fun getDetailPlan(plan: PlanStateModel){
        viewModelScope.launch {
            planStateData.value = plan
            planRepository.getPlan(plan.TripId).mapCatching { response ->
                if(response.status){
                    _drawUpPlanData.emit(UiState.Success(
                        convertDetailPlanUseCase(plan.StartDate, plan.EndDate, response.data.plans)
                    ))
                    _planData.emit(UiState.Success(response.data))
                }else{
                    _drawUpPlanEvent.emit(UiEvent.Failure(response.reason))
                }
            }.onFailure { e->
                e.printStackTrace()
            }
        }
    }

    fun outPlan(){
        viewModelScope.launch {
            planRepository.outPlan(requireNotNull(planStateData.value?.TripId)).mapCatching {
                if(it.status){
                    _drawUpPlanEvent.emit(UiEvent.Success(Unit))
                }else{
                    _drawUpPlanEvent.emit(UiEvent.Failure("일정에 참가하지 못하였습니다."))
                }
            }.onFailure {
                _drawUpPlanEvent.emit(UiEvent.Failure("네트워크를 확인해 주세요."))
            }
        }
    }

    //planState가 mapping 된 후 부터 사용 가능
    fun refreshPlan(){
        viewModelScope.launch {
            planRepository.getPlan(requireNotNull(planStateData.value?.TripId)).mapCatching { response ->
                if(response.status){
                    _drawUpPlanData.emit(UiState.Success(
                        convertDetailPlanUseCase(response.data.startDate, response.data.endDate, response.data.plans)
                    ))
                    _planData.emit(UiState.Success(response.data))
                }else{
                    _drawUpPlanEvent.emit(UiEvent.Failure(response.reason))
                }
            }.onFailure { e->
                e.printStackTrace()
            }
        }
    }
}