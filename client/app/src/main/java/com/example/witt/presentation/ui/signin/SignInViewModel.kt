package com.example.witt.presentation.ui.signin

import android.app.Application
<<<<<<< HEAD
import android.content.ContentValues.TAG
import android.provider.Settings.Global.getString
import android.util.Log
=======
import android.content.SharedPreferences
>>>>>>> feature/#29/store_profile_data_local
import androidx.lifecycle.*
import com.example.witt.R
import com.example.witt.domain.use_case.remote.SignInEmailPassword
import com.example.witt.domain.use_case.remote.UserTokenSignIn
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.NaverIdLoginSDK.oauthLoginCallback
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.internal.Contexts
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
            is SignInEvent.NaverSignIn -> {
                naverLogin()
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

<<<<<<< HEAD
    fun naverLogin(){
        viewModelScope.launch {
            if (handleNaverLogin()){
                signInEventChannel.trySend(SignInUiEvent.Success)
                NaverIdLoginSDK.logout()
=======
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
>>>>>>> feature/#29/store_profile_data_local
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

    var profileImage: String = ""
    var nickname: String = ""

    private suspend fun handleNaverLogin():Boolean =
        suspendCancellableCoroutine<Boolean>{ continuation2 ->
        val oAuthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                val naverAccessToken = NaverIdLoginSDK.getAccessToken()
                Log.e(TAG, "naverAccessToken : $naverAccessToken")
                NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                    override fun onSuccess(result: NidProfileResponse) {
                        continuation2.resume(true)
                        nickname = result.profile?.nickname.toString()
                        profileImage = result.profile?.profileImage.toString()
                        Log.e(TAG, "네이버 로그인한 유저 정보 - 이름 : $nickname")
                        Log.e(TAG, "네이버 로그인한 유저 정보 - 이메일 : $profileImage")
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
                continuation2.resume(false)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                signInEventChannel.trySend(SignInUiEvent.Failure("로그인 실패"))
                continuation2.resume(false)
            }
        }
           NaverIdLoginSDK.authenticate(getApplication(), oAuthLoginCallback)
    }


}


