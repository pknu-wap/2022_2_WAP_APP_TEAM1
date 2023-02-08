package com.example.witt.presentation.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.domain.use_case.remote.SignInEmailPassword
import com.example.witt.domain.use_case.remote.SocialSignIn
import com.example.witt.domain.use_case.remote.TokenSignIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInEmailPassword: SignInEmailPassword,
    private val tokenSign: TokenSignIn,
    private val socialSignIn: SocialSignIn,
) : ViewModel() {

    companion object {
        const val NAVER_TYPE = 2
        const val KAKAO_TYPE = 1
        const val EMAIL_TYPE = 0
    }

    val inputEmail: MutableLiveData<String> = MutableLiveData()
    val inputPassword: MutableLiveData<String> = MutableLiveData()

    private val signInEventChannel = Channel<SignInUiEvent>()
    val signInEvents = signInEventChannel.receiveAsFlow()

    private val oauthId: MutableLiveData<String> = MutableLiveData()
    private val accountType: MutableLiveData<Int> = MutableLiveData()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.Submit -> {
                emailPasswordSignIn()
            }
            is SignInEvent.KakaoSignIn -> {
                oauthId.value = event.oauthId
                accountType.value = KAKAO_TYPE
                socialSignIn()
            }
            is SignInEvent.NaverSignIn -> {
                oauthId.value = event.oauthId
                accountType.value = NAVER_TYPE
                socialSignIn()
            }
            is SignInEvent.CheckToken -> {
                tokenSignIn()
            }
        }
    }

    private fun emailPasswordSignIn() {
        // data 전송
        viewModelScope.launch {
            if (!inputEmail.value.isNullOrBlank() && !inputPassword.value.isNullOrBlank()) {
                val signInResult =
                    signInEmailPassword(EMAIL_TYPE, inputEmail.value ?: "", inputPassword.value ?: "")
                signInResult.mapCatching {
                    if (it.status) {
                        if (it.isProfileExists) {
                            signInEventChannel.trySend(SignInUiEvent.Success)
                        } else {
                            signInEventChannel.trySend(SignInUiEvent.HasNoProfile)
                        }
                    } else {
                        signInEventChannel.trySend(SignInUiEvent.Failure(it.reason))
                    }
                }
            } else {
                signInEventChannel.trySend(SignInUiEvent.Failure("이메일과 비밀번호를 확인해주세요."))
            }
        }
    }

    private fun socialSignIn() { // 소셜 로그인
        viewModelScope.launch {
            val signInResult =
                socialSignIn(accountType.value ?: -1, oauthId.value ?: "") // type 설정해야함.
            signInResult.mapCatching {
                if (it.status) { // 로그인 성공
                    if (it.isProfileExists) { // 프로필이 존재할 때
                        signInEventChannel.trySend(SignInUiEvent.Success)
                    } else { // 프로필이 존재하지 않을 때
                        signInEventChannel.trySend(SignInUiEvent.SuccessSocialLogin)
                    }
                } else {
                    signInEventChannel.trySend(SignInUiEvent.Failure(it.reason))
                }
            }
        }
    }

    private fun tokenSignIn() {
        viewModelScope.launch {
            tokenSign().mapCatching { response ->
                if (response.status) {
                    signInEventChannel.trySend(SignInUiEvent.Success)
                }
            }
        }
    }

    sealed class SignInUiEvent {
        data class Failure(val message: String?) : SignInUiEvent()
        object SuccessSocialLogin : SignInUiEvent()
        object Success : SignInUiEvent()
        object HasNoProfile : SignInUiEvent()
    }
}
