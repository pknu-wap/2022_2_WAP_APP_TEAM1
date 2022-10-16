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
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
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

    //socialProfile 객체 생성
    val socialProfile = SocialProfile("","","")

    fun onEvent(event: SignInEvent){
        when(event) {
            is SignInEvent.Submit -> {
                submitData()
            }
            is SignInEvent.KakaoSignIn ->{
                kakaoLogin()
            }
            is SignInEvent.NaverSignIn -> {
                naverLogin()
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
                getKakaoUserInfo()
                signInEventChannel.trySend(SignInUiEvent.Success)
            }
        }
    }


    private fun naverLogin(){
        viewModelScope.launch {
            if (handleNaverLogin()) {
                signInEventChannel.trySend(SignInUiEvent.Success)
                NaverIdLoginSDK.logout()
            }
        }
    }

    private fun tokenSignIn(){
        viewModelScope.launch {
            userTokenSign().mapCatching { response ->
                if(response.status){
                    signInEventChannel.trySend(SignInUiEvent.Success)
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
    private suspend fun getKakaoUserInfo():Boolean =
        suspendCancellableCoroutine<Boolean> { continuation ->
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                signInEventChannel.trySend(SignInUiEvent.Failure("사용자 정보 요청 실패"))
                continuation.resume(false)
            }
            else if (user != null) {
                socialProfile.socialId = user.id.toString()
                socialProfile.nickName = user.kakaoAccount?.profile?.nickname
                socialProfile.profileImage=user.kakaoAccount?.profile?.thumbnailImageUrl
                continuation.resume(true)
            }
        }
    }

    sealed class SignInUiEvent{
        data class Failure(val message: String?): SignInUiEvent()
        object Success: SignInUiEvent()
    }

    //naver login
    private suspend fun handleNaverLogin():Boolean =
        suspendCancellableCoroutine{ continuation ->
        val oAuthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                    override fun onSuccess(result: NidProfileResponse) {
                        socialProfile.nickName = result.profile?.nickname.toString()
                        socialProfile.profileImage = result.profile?.profileImage.toString()
                        socialProfile.socialId = result.profile?.id.toString()
                        continuation.resume(true)
                    }
                    override fun onError(errorCode: Int, message: String) {
                        signInEventChannel.trySend(SignInUiEvent.Failure("사용자 정보 가져오기 오류"))
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        signInEventChannel.trySend(SignInUiEvent.Failure("사용자 정보 가져오기 실패"))
                    }
                })
            }

            override fun onError(errorCode: Int, message: String) {
                signInEventChannel.trySend(SignInUiEvent.Failure("로그인 에러"))
                continuation.resume(false)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                signInEventChannel.trySend(SignInUiEvent.Failure("로그인 실패"))
                continuation.resume(false)
            }
        }
           NaverIdLoginSDK.authenticate(application, oAuthLoginCallback)
    }
}


