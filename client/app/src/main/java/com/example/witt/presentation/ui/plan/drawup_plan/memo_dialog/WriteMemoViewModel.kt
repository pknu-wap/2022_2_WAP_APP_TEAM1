package com.example.witt.presentation.ui.plan.drawup_plan.memo_dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.repository.PlanRepository
import com.example.witt.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteMemoViewModel @Inject constructor(
    private val planRepository: PlanRepository
): ViewModel() {

    //dataBinding
    val inputMemo : MutableLiveData<String> = MutableLiveData()

    private val _writeMemoEvent = MutableSharedFlow<UiEvent<Unit>>()
    val writeMemoEvent: SharedFlow<UiEvent<Unit>> get() = _writeMemoEvent

    private val tripId : MutableLiveData<Int> = MutableLiveData()
    private val dayId : MutableLiveData<Int> = MutableLiveData()

    fun setTripId(memoTripId: Int){
        tripId.value = memoTripId
    }

    fun setMemoInfo(memo: String, memoDay: Int){
        inputMemo.value = memo
        dayId.value = memoDay
    }

    fun submitMemo(){
        viewModelScope.launch {
            if(!inputMemo.value.isNullOrEmpty()){
                planRepository.makeMemo(
                    tripId = requireNotNull(tripId.value),
                    day = requireNotNull(dayId.value),
                    content = requireNotNull(inputMemo.value)
                ).mapCatching {
                    if(it.status){
                        _writeMemoEvent.emit(UiEvent.Success(Unit))
                    }else{
                        _writeMemoEvent.emit(UiEvent.Failure(it.reason))
                    }
                }.onFailure {
                    _writeMemoEvent.emit(UiEvent.Failure("네트워크를 확인해주세요."))
                }
            }else{
                _writeMemoEvent.emit(UiEvent.Failure("내용을 확인해주세요."))
            }
        }
    }
}