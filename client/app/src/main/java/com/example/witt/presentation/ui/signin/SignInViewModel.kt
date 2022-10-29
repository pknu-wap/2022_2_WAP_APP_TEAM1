package com.example.witt.presentation.ui.signin

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.witt.di.IoDispatcher
import com.example.witt.domain.use_case.remote.SignInEmailPassword
import com.example.witt.domain.use_case.remote.SocialSignIn
import com.example.witt.domain.use_case.remote.TokenSignIn
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInEmailPassword: SignInEmailPassword,
    private val tokenSign: TokenSignIn,
    private val application: Application,
    private val socialSignIn: SocialSignIn,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): ViewModel() {

    companion object {
        const val NAVER_TYPE = 2
        const val KAKAO_TYPE = 1
        const val EMAIL_TYPE = 0
    }

    val inputEmail : MutableLiveData<String> = MutableLiveData()
    val inputPassword : MutableLiveData<String> = MutableLiveData()

    private val signInEventChannel = Channel<SignInUiEvent>()
    val signInEvents = signInEventChannel.receiveAsFlow()

    private val oauthId : MutableLiveData<String> = MutableLiveData()
    private val socialNickName : MutableLiveData<String> = MutableLiveData()
    private val socialProfileImage : MutableLiveData<String> = MutableLiveData()

    fun onEvent(event: SignInEvent){
        when(event) {
            is SignInEvent.Submit -> {
                emailPasswordSignIn()
            }
            is SignInEvent.KakaoSignIn ->{
                kakaoSignIn()
            }
            is SignInEvent.NaverSignIn -> {
                naverSignIn()
            }
            is SignInEvent.CheckToken -> {
                tokenSignIn()
            }
        }
    }

    private fun emailPasswordSignIn(){
        //data 전송
        viewModelScope.launch {
            if(!inputEmail.value.isNullOrBlank() && !inputPassword.value.isNullOrBlank()){
                val signInResult =
                    signInEmailPassword(EMAIL_TYPE, inputEmail.value ?: "", inputPassword.value ?: "")
                signInResult.mapCatching {
                    if(it.status){
                        if(it.isProfileExists){
                            signInEventChannel.trySend(SignInUiEvent.Success)
                        }else {
                            signInEventChannel.trySend(SignInUiEvent.HasNoProfile)
                        }
                    }else{
                        signInEventChannel.trySend(SignInUiEvent.Failure(it.reason))
                    }
                }
            }else{
                signInEventChannel.trySend(SignInUiEvent.Failure("이메일과 비밀번호를 확인해주세요."))
            }
        }
    }

    private fun kakaoSignIn(){
        viewModelScope.launch(coroutineDispatcher) {
            if(handleKakaoSignIn()) { //사용자 카카오 로그인
              if(getKakaoUserInfo()){
                  val signInResult =
                      socialSignIn(KAKAO_TYPE, oauthId.value ?: "")
                  signInResult.mapCatching {
                      if (it.status) { //로그인 성공
                          if(it.isProfileExists){ //프로필이 존재할 때
                              signInEventChannel.trySend(SignInUiEvent.Success)
                          }else { //프로필이 존재하지 않을 때
                              signInEventChannel.trySend(SignInUiEvent.SuccessSocialLogin(
                                  socialProfileImage.value ?: "", socialNickName.value ?: ""))
                          }
                      } else {
                          signInEventChannel.trySend(SignInUiEvent.Failure(it.reason))
                      }
                  }
              }
            } else {
                signInEventChannel.trySend(SignInUiEvent.Failure(""))
            }
        }
    }


    private fun naverSignIn(){
        viewModelScope.launch {
            if (handleNaverSignIn()) {
                val signInResult =
                    socialSignIn(NAVER_TYPE, oauthId.value ?: "")
                signInResult.mapCatching {
                    if (it.status) { //로그인 성공
                        if(it.isProfileExists){ //프로필이 존재할 때
                            signInEventChannel.trySend(SignInUiEvent.Success)
                        }else { //프로필이 존재하지 않을 때
                            signInEventChannel.trySend(SignInUiEvent.SuccessSocialLogin(
                                socialProfileImage.value ?: "", socialNickName.value ?: ""))
                        }
                        NaverIdLoginSDK.logout()
                    }else{
                        signInEventChannel.trySend(SignInUiEvent.Failure(it.reason))
                    }
                }
            }else{
                signInEventChannel.trySend(SignInUiEvent.Failure("인터넷 연결을 확인해주세요."))
            }
        }
    }

    private fun tokenSignIn(){
        viewModelScope.launch {
            tokenSign().mapCatching { response ->
                if(response.status){
                    signInEventChannel.trySend(SignInUiEvent.Success)
                }
            }
        }
    }

    private suspend fun handleKakaoSignIn() : Boolean =
        suspendCancellableCoroutine { continuation ->
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
                    viewModelScope.launch {
                        continuation.resume(true)
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(application, callback = callback)
        }
    }

    private suspend fun getKakaoUserInfo() : Boolean =
        suspendCancellableCoroutine { continuation ->
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                signInEventChannel.trySend(SignInUiEvent.Failure("사용자 정보 요청 실패"))
                continuation.resume(false)
            }
            user?.let{
                oauthId.postValue(user.id.toString())
                socialNickName.postValue(user.kakaoAccount?.profile?.nickname ?:  "")
                socialProfileImage.postValue(user.kakaoAccount?.profile?.thumbnailImageUrl ?: "")
                continuation.resume(true)
            }
        }
    }

    //naver login
    private suspend fun handleNaverSignIn():Boolean =
        suspendCancellableCoroutine{ continuation ->
        val oAuthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                    override fun onSuccess(result: NidProfileResponse) {
                        result.profile?.let{ profile ->
                            socialProfileImage.postValue(profile.profileImage ?: "")
                            socialNickName.postValue(profile.nickname ?: "")
                            oauthId.postValue(profile.id ?: "")
                            continuation.resume(true)
                        }
                    }
                    override fun onError(errorCode: Int, message: String) {
                        signInEventChannel.trySend(SignInUiEvent.Failure("사용자 정보 가져오기 오류"))
                        continuation.resume(false)
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        signInEventChannel.trySend(SignInUiEvent.Failure("사용자 정보 가져오기 실패"))
                        continuation.resume(false)
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
            //todo activity context를 넣어줘야함
           NaverIdLoginSDK.authenticate(application, oAuthLoginCallback)
    }

    sealed class SignInUiEvent{
        data class Failure(val message: String?): SignInUiEvent()
        data class SuccessSocialLogin(val profileImage: String, val nickName: String): SignInUiEvent()
        object Success: SignInUiEvent()
        object HasNoProfile: SignInUiEvent()
    }
}


