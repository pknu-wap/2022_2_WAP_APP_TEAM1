package com.example.witt.presentation.ui.plan.drawup_plan.memo_dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.repository.DetailPlanRepository
import com.example.witt.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteMemoViewModel @Inject constructor(
    private val repository: DetailPlanRepository
) : ViewModel() {

    // dataBinding
    val inputMemo: MutableLiveData<String> = MutableLiveData()

    private val _writeMemoEvent = MutableSharedFlow<UiEvent<Unit>>()
    val writeMemoEvent: SharedFlow<UiEvent<Unit>> get() = _writeMemoEvent

    private val tripId: MutableLiveData<Int> = MutableLiveData()

    private val planId: MutableLiveData<Int> = MutableLiveData()

    private val dayId: MutableLiveData<Int> = MutableLiveData()

    fun setTripId(memoTripId: Int) {
        tripId.value = memoTripId
    }

    fun setMemoInfo(memo: String, memoDay: Int?, memoPlanId: Int?) {
        inputMemo.value = memo
        memoDay?.let { dayId.value = it }
        memoPlanId?.let { planId.value = it }
    }

    fun submitMemo() {
        // planId를 가지면 edit, 아니면 add
        if (planId.value != -1) {
            editMemo()
        } else {
            addMemo()
        }
    }

    private fun addMemo() {
        viewModelScope.launch {
            if (!inputMemo.value.isNullOrEmpty()) {
                repository.makeMemo(
                    tripId = requireNotNull(tripId.value),
                    day = requireNotNull(dayId.value),
                    content = requireNotNull(inputMemo.value)
                ).mapCatching {
                    if (it.status) {
                        _writeMemoEvent.emit(UiEvent.Success(Unit))
                    } else {
                        _writeMemoEvent.emit(UiEvent.Failure(it.reason))
                    }
                }.onFailure {
                    _writeMemoEvent.emit(UiEvent.Failure("네트워크를 확인해주세요."))
                }
            } else {
                _writeMemoEvent.emit(UiEvent.Failure("내용을 확인해주세요."))
            }
        }
    }

    private fun editMemo() {
        viewModelScope.launch {
            if (!inputMemo.value.isNullOrEmpty()) {
                repository.editMemo(
                    tripId = requireNotNull(tripId.value),
                    planId = requireNotNull(planId.value),
                    Content = requireNotNull(inputMemo.value)
                ).mapCatching {
                    if (it.status) {
                        _writeMemoEvent.emit(UiEvent.Success(Unit))
                    } else {
                        _writeMemoEvent.emit(UiEvent.Failure(it.reason))
                    }
                }.onFailure {
                    _writeMemoEvent.emit(UiEvent.Failure("네트워크를 확인해주세요."))
                }
            } else {
                _writeMemoEvent.emit(UiEvent.Failure("내용을 확인해주세요."))
            }
        }
    }
}
