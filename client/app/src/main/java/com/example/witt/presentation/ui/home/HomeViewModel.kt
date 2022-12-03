package com.example.witt.presentation.ui.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.model.use_case.plan.PlanStateModel
import com.example.witt.domain.model.remote.plan.get_plan.GetPlanListModel
import com.example.witt.domain.model.remote.plan.get_plan.toPlanStateModel
import com.example.witt.domain.repository.PlanRepository
import com.example.witt.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val planRepository: PlanRepository,
    private val prefs : SharedPreferences
): ViewModel() {

    private val _planList = MutableStateFlow(GetPlanListModel(false, "", listOf()))
    val planList: StateFlow<GetPlanListModel> get() = _planList

    private val _joinPlanUiEvent = MutableSharedFlow<UiEvent<PlanStateModel>>()
    val joinPlanUiEvent: SharedFlow<UiEvent<PlanStateModel>> get() = _joinPlanUiEvent

    private val _homeEvent : MutableSharedFlow<UiEvent<Unit>> = MutableSharedFlow()
    val homeEvent : SharedFlow<UiEvent<Unit>> get() = _homeEvent

    init{
        getPlanList()
    }

    fun getPlanList(){
        viewModelScope.launch {
            planRepository.getPlanList().mapCatching {
                _planList.emit(it)
            }.onFailure {
                _homeEvent.emit(UiEvent.Failure("일정을 불러오는데 실패하였습니다."))
            }
        }
    }

    fun removePlan(tripId: Int){
        viewModelScope.launch {
            planRepository.outPlan(tripId).mapCatching { response ->
                if(response.status){
                    getPlanList()
                }else{
                    _homeEvent.emit(UiEvent.Failure(response.reason))
                }
            }.onFailure {
                _homeEvent.emit(UiEvent.Failure("일정을 불러오는데 실패하였습니다."))
            }
        }
    }

    fun joinPlan(tripId: Int){
        viewModelScope.launch {
            planRepository.joinPlan(tripId).mapCatching {
                if(it.status){
                    getJoinPlan()
                }else{
                    _joinPlanUiEvent.emit(UiEvent.Failure("일정에 참가하지 못하였습니다."))
                }
            }.onFailure {
                _joinPlanUiEvent.emit(UiEvent.Failure("네트워크를 확인해 주세요."))
            }
        }
    }

    private fun getJoinPlan(){
        viewModelScope.launch {
            planRepository.getPlanList().mapCatching { response ->
                if(response.status){
                    rejectPlan()
                    _joinPlanUiEvent.emit(UiEvent.Success(response.result.last().toPlanStateModel()))
                }else{
                    _joinPlanUiEvent.emit(UiEvent.Failure(response.reason))
                }
            }.onFailure {
                _joinPlanUiEvent.emit(UiEvent.Failure("일정을 불러오는데 실패하였습니다."))
            }
        }
    }

    fun rejectPlan(){
        viewModelScope.launch {
            prefs.edit()
                .remove("tripId")
                .remove("tripName")
                .remove("tripDate")
                .apply()
        }
    }
}