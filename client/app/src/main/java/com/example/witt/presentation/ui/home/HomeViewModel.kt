package com.example.witt.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.model.plan.GetPlanModel
import com.example.witt.domain.repository.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val planRepository: PlanRepository
): ViewModel() {

    private val _planList = MutableStateFlow<List<GetPlanModel>>(listOf())
    val planList: StateFlow<List<GetPlanModel>> get() = _planList

    private val _homeEvent = MutableSharedFlow<UiEvent<Unit>>()
    val homeEvent: SharedFlow<UiEvent<Unit>> get() = _homeEvent

    init{
        getPlanList()
    }

    fun getPlanList(){
        viewModelScope.launch {
            planRepository.getPlanList().mapCatching {
                _planList.value = it
            }.onFailure {
                _homeEvent.emit(UiEvent.Failure("일정을 불러오는데 실패하였습니다."))
            }
        }
    }
    sealed class UiEvent<out T> {
        data class Success<T>(val data: T) : UiEvent<T>()
        data class Failure(val message: String?) : UiEvent<Nothing>()
    }
}