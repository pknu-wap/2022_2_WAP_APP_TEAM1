package com.example.witt.presentation.ui.signin

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import com.example.witt.domain.use_case.remote.SignInEmailPassword
import com.example.witt.domain.use_case.remote.UserTokenSignIn
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInEmailPassword: SignInEmailPassword,
    private val userTokenSign: UserTokenSignIn,
    private val application: Application,
    private val prefs: SharedPreferences
): ViewModel() {

    val inputEmail : MutableLiveData<String> = MutableLiveData()
    val inputPassword : MutableLiveData<String> = MutableLiveData()

    private val signInEventChannel = Channel<SignInUiEvent>()
    val signInEvents = signInEventChannel.receiveAsFlow()

    fun onEvent(event: SignInEvent){
        when(event) {
            is SignInEvent.Submit -> {
                submitData()
            }
            is SignInEvent.KakaoSignIn ->{
                kakaoLogin()
            }
            is SignInEvent.CheckToken -> {
                tokenSignIn()
            }
        }
    }

    private fun submitData(){
        //data 전송
        viewModelScope.launch {
            if(!inputEmail.value.isNullOrBlank() && !inputPassword.value.isNullOrBlank()){
                val signInResult =
                    signInEmailPassword(accountType = 0, inputEmail.value ?: "", inputPassword.value ?: "")
                signInResult.mapCatching {
                    if(it.status){
                        signInEventChannel.trySend(SignInUiEvent.Success)
                    }else{
                        signInEventChannel.trySend(SignInUiEvent.Failure(it.reason))
                    }
                }
            }else{
                signInEventChannel.trySend(SignInUiEvent.Failure("이메일과 비밀번호를 확인해주세요."))
            }
        }
    }

    private fun kakaoLogin(){
        viewModelScope.launch {
            if (handleKakaoLogin()){
                signInEventChannel.trySend(SignInUiEvent.Success)
            }
        }
    }

    private fun tokenSignIn(){
        viewModelScope.launch {

            val accessToken = prefs.getString("accessToken", null)
            val refreshToken = prefs.getString("refreshToken", null)

            if(!accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank()){
                userTokenSign(accessToken, refreshToken).mapCatching { response ->
                    if(response.status){
                        signInEventChannel.trySend(SignInUiEvent.Success)
                    }
                }
            }
        }
    }

    private suspend fun handleKakaoLogin():Boolean =
    suspendCancellableCoroutine<Boolean>{ continuation ->
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                signInEventChannel.trySend(SignInUiEvent.Failure("카카오계정으로 로그인 실패"))
                continuation.resume(false)
            } else if (token != null) {
                continuation.resume(true)
            }
        }

// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(application)) {
            UserApiClient.instance.loginWithKakaoTalk(application) { token, error ->
                if (error != null) {
                    signInEventChannel.trySend(SignInUiEvent.Failure("카카오톡으로 로그인 실패"))
                    continuation.resume(false)
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(application, callback = callback)
                } else if (token != null) {
                    continuation.resume(true)

                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(application, callback = callback)
        }
    }

//    private fun getUserInfo(){
//        UserApiClient.instance.me { user, error ->
//            if (error != null) {
//                Log.e(TAG, "사용자 정보 요청 실패", error)
//            }
//            else if (user != null) {
//                Log.i(TAG, "사용자 정보 요청 성공" +
//                        "\n회원번호: ${user.id}" +
//                        "\n이메일: ${user.kakaoAccount?.email}" +
//                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
//                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
//            }
//        }
//    }
    sealed class SignInUiEvent{
        data class Failure(val message: String?): SignInUiEvent()
        object Success: SignInUiEvent()
    }
}
