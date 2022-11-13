package com.example.witt.presentation.ui.plan.make_plan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.model.plan.MakePlanModel
import com.example.witt.domain.use_case.remote.MakePlanUseCase
import com.example.witt.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakePlanViewModel @Inject constructor(
    private val makePlanUseCase: MakePlanUseCase
): ViewModel() {

    private val _planDestination: MutableStateFlow<String> = MutableStateFlow("완료")
    val planDestination : StateFlow<String> get() = _planDestination

    private val _makePlanEvent = MutableSharedFlow<UiEvent<Unit>>()
    val makePlanEvent: SharedFlow<UiEvent<Unit>> get() = _makePlanEvent

    val inputPlanId: MutableLiveData<String> = MutableLiveData("")

    fun onButtonEvent(destination: String) {
        _planDestination.value = destination
    }

    fun submitPlan(startDate: String, endDate: String){
        viewModelScope.launch {
            makePlanUseCase(
                MakePlanModel(
                    StartDate = startDate,
                    EndDate = endDate,
                    Name = requireNotNull(inputPlanId.value),
                    Region = requireNotNull(planDestination.value)
                )
            ).mapCatching { response ->
                if(response.status){
                    _makePlanEvent.emit(UiEvent.Success(Unit))
                }else{
                    _makePlanEvent.emit(UiEvent.Failure(response.reason))
                }
            }
        }
    }
}
