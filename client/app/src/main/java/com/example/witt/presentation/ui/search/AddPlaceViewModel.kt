package com.example.witt.presentation.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.model.plan.detail_plan.AddPlaceModel
import com.example.witt.domain.repository.DetailPlanRepository
import com.example.witt.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlaceViewModel @Inject constructor(
    private val repository: DetailPlanRepository
): ViewModel() {

    private val _addPlaceEvent = MutableSharedFlow<UiEvent<Unit>>()
    val addPlaceEvent: SharedFlow<UiEvent<Unit>> get() = _addPlaceEvent

    private val dayId : MutableLiveData<Int> = MutableLiveData()

    private val tripId : MutableLiveData<Int> = MutableLiveData()

    fun setInfo(dayId: Int, tripId: Int){
        this.dayId.value = dayId
        this.tripId.value = tripId
    }

    fun addPlace(place: AddPlaceModel){
        viewModelScope.launch {
            repository.addPlace(requireNotNull(dayId.value), requireNotNull(tripId.value), place)
                .mapCatching { response ->
                    if(response.status){
                        _addPlaceEvent.emit(UiEvent.Success(Unit))
                    }else{
                        _addPlaceEvent.emit(UiEvent.Failure(response.reason))
                    }
            }.onFailure {
                 _addPlaceEvent.emit(UiEvent.Failure("네트워크를 확인해주세요."))
            }
        }
    }
}