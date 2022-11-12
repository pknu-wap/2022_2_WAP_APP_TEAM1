package com.example.witt.presentation.ui.signin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.witt.R
import com.example.witt.databinding.FragmentSignInBinding
import com.example.witt.presentation.base.BaseFragment
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    private val viewModel : SignInViewModel by viewModels()
    private val userApiClient = UserApiClient.instance
    private val prefs: SharedPreferences by lazy { requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)}

    private val kakaoSignInCallback: (OAuthToken?, Throwable?) -> Unit = { token, _ ->
        if (token != null) {
            Toast.makeText(requireContext(), "카카오계정 로그인 성공 ", Toast.LENGTH_SHORT).show()
            userApiClient.me { user, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), "카카오계정 사용자 정보 가져오기 실패", Toast.LENGTH_SHORT).show()
                } else if (user != null) {
                    Toast.makeText(requireContext(), "카카오계정 사용자 정보 가져오기 성공 = " + user.kakaoAccount?.email
                        , Toast.LENGTH_SHORT).show()
                    val oauthId = user.id.toString()
                    val profile = user.kakaoAccount?.profile?.thumbnailImageUrl
                    val nickName = user.kakaoAccount?.profile?.nickname
                    initDataStore(nickName ?: "", profile ?: "")
                    viewModel.onEvent(SignInEvent.KakaoSignIn(oauthId))
                }
            }
        } else {
            Toast.makeText(requireContext(), "다시 로그인 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private val naverSignInCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                override fun onSuccess(result: NidProfileResponse) {
                    result.profile?.let{ user ->
                        val oauthId = user.id.toString()
                        val profile = user.profileImage
                        val nickName = user.nickname
                        initDataStore(nickName ?: "", profile ?: "")
                        viewModel.onEvent(SignInEvent.NaverSignIn(oauthId))
                    }
                }
                override fun onError(errorCode: Int, message: String) {
                    Toast.makeText(requireContext(), "사용자 정보 가져오기 오류", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    Toast.makeText(requireContext(), "사용자 정보 가져오기 실패", Toast.LENGTH_SHORT).show()
                }
            })
        }
        override fun onError(errorCode: Int, message: String) {
            Toast.makeText(requireContext(), "다시 로그인 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show()
        }
        override fun onFailure(httpStatus: Int, message: String) {
            Toast.makeText(requireContext(), "다시 로그인 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //dataBinding viewModel
        binding.viewModel = viewModel

        //token 검사
        //viewModel.onEvent(SignInEvent.CheckToken)

        initSignInUpButton()
        initKakaoButton()
        initNaverButton()
        initChannel()

    }
    private fun initSignInUpButton(){
        binding.signInButton.setOnClickListener {
            viewModel.onEvent(SignInEvent.Submit)
        }

        binding.goToSignUpButton.setOnClickListener{
            val direction = SignInFragmentDirections.actionSignInFragmentToPlanNav()
            findNavController().navigate(direction)
        }
    }

    private fun initKakaoButton() {
        binding.kakaoSignInButton.setOnClickListener {
            if (userApiClient.isKakaoTalkLoginAvailable(requireContext())) {
                userApiClient.loginWithKakaoTalk(
                    requireContext(),
                    callback = kakaoSignInCallback
                )
            } else {
                userApiClient.loginWithKakaoAccount(
                    requireContext(),
                    callback = kakaoSignInCallback
                )
            }
        }
    }

    private fun initNaverButton(){
        binding.naverSignInButton.setOnClickListener {
            NaverIdLoginSDK.authenticate(requireActivity(), naverSignInCallback)
        }
    }

    private fun initDataStore(nickName: String, profile: String){
        prefs.edit()
            .putString("profile", profile)
            .putString("nickName", nickName)
            .apply()
    }

    private fun initChannel(){
        lifecycleScope.launch{
            viewModel.signInEvents.collect { event ->
                when(event){
                    is SignInViewModel.SignInUiEvent.Success ->{ //프로필 있는 상태에서 로그인 성공시 홈으로
                        val direction = SignInFragmentDirections.actionSignInFragmentToPlanNav()
                        findNavController().navigate(direction)
                    }
                    is SignInViewModel.SignInUiEvent.Failure ->{ //실패시 Toast 메세지
                        Toast.makeText(activity, event.message, Toast.LENGTH_SHORT).show()
                    }
                    is SignInViewModel.SignInUiEvent.SuccessSocialLogin ->{ //프로필 없는 상태에서 소셜 로그인 성공
                        val direction = SignInFragmentDirections.actionSignInFragmentToProfileEditFragment()
                        findNavController().navigate(direction)
                    }
                    is SignInViewModel.SignInUiEvent.HasNoProfile ->{ //프로필 없는 상태에서 이메일 로그인 성공
                        val direction = SignInFragmentDirections.actionSignInFragmentToProfileEditFragment()
                        findNavController().navigate(direction)
                    }
                }
            }
        }
    }
}