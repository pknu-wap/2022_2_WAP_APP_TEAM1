package com.example.witt.presentation.ui.signin

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.witt.domain.use_case.remote.SignInEmailPassword
import com.example.witt.domain.use_case.validate.ValidateEmail
import com.example.witt.domain.use_case.validate.ValidatePassword
import com.example.witt.presentation.ui.signup.SignUpEvent
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInEmailPassword: SignInEmailPassword,
    application: Application
): AndroidViewModel(application) {

    val inputEmail : MutableLiveData<String> = MutableLiveData()
    val inputPassword : MutableLiveData<String> = MutableLiveData()

    private val signInEventChannel = Channel<SignInUiEvent>()
    val signInEvents = signInEventChannel.receiveAsFlow()

    private val context = application.applicationContext

    companion object{
        const val  TAG = "KakaoAuthViewModel"
    }

    fun onEvent(event: SignInEvent){
        when(event){
            is SignInEvent.Submit ->{
                submitData()
            }
        }
    }

    private fun submitData(){
        //data 전송
        viewModelScope.launch {
            if(!inputEmail.value.isNullOrBlank() && !inputPassword.value.isNullOrBlank()){
                val signInResult =
                    signInEmailPassword(accountType = 0, inputEmail.value ?: "", inputPassword.value ?: "")
                if(signInResult.isSuccess){
                    signInEventChannel.trySend(SignInUiEvent.Success)
                }else{
                    signInEventChannel.trySend(SignInUiEvent.Failure("네트워크 문제가 발생하였습니다."))
                }
            }else{
                signInEventChannel.trySend(SignInUiEvent.Failure("이메일과 비밀번호를 확인해주세요."))
            }
        }
    }
    sealed class SignInUiEvent{
        data class Failure(val message: String?): SignInUiEvent()
        object Success: SignInUiEvent()
    }

    val isLoggedIn = MutableStateFlow<Boolean>(false)

    fun kakaoLogin(){
        viewModelScope.launch {
            isLoggedIn.emit(handleKakaoLogin())
        }
    }


    private suspend fun handleKakaoLogin():Boolean =
    suspendCoroutine<Boolean>{ continuation ->
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
                continuation.resume(false)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                continuation.resume(true)
                getTokenInfo()
                getUserInfo()
            }
        }

// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")

                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }

    }

    private fun getTokenInfo(){
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e(TAG, "토큰 정보 보기 실패", error)
            }
            else if (tokenInfo != null) {
                Log.i(TAG, "토큰 정보 보기 성공" +
                        "\n회원번호: ${tokenInfo.id}" +
                        "\n만료시간: ${tokenInfo.expiresIn} 초")
            }
        }
    }
    private fun getUserInfo(){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(TAG, "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
            }
        }
    }
}