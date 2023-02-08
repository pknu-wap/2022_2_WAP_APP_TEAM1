package com.example.witt.data.repository

import android.content.SharedPreferences
import com.example.witt.data.mapper.toSignInModel
import com.example.witt.data.mapper.toSignUpModel
import com.example.witt.data.mapper.toSocialSignInModel
import com.example.witt.data.mapper.toTokenModel
import com.example.witt.data.model.remote.signin.request.SignInRequest
import com.example.witt.data.model.remote.signin.request.SocialSignInRequest
import com.example.witt.data.model.remote.signup.request.SignUpRequest
import com.example.witt.data.source.remote.signin.SignInDataSource
import com.example.witt.data.source.remote.signin.social_signin.SocialSignInDataSource
import com.example.witt.data.source.remote.signin.token_signin.TokenSignInDataSource
import com.example.witt.data.source.remote.signup.SignUpDataSource
import com.example.witt.domain.model.remote.signin.SignInModel
import com.example.witt.domain.model.remote.signin.SocialSignInModel
import com.example.witt.domain.model.remote.signin.TokenModel
import com.example.witt.domain.model.remote.signup.SignUpModel
import com.example.witt.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val signInDataSource: SignInDataSource,
    private val signUpDataSource: SignUpDataSource,
    private val tokenSignInDataSource: TokenSignInDataSource,
    private val socialSignInDataSource: SocialSignInDataSource,
    private val prefs: SharedPreferences
) : AuthRepository {

    override suspend fun signIn(accountType: Int, email: String, password: String): Result<SignInModel> {
        return signInDataSource.signIn(
            SignInRequest(
                AccountType = accountType,
                Username = email,
                Password = password
            )
        ).mapCatching { response ->
            prefs.edit()
                .putString("accessToken", response.AccessToken)
                .putString("refreshToken", response.RefreshToken)
                .apply()
            response.toSignInModel()
        }
    }

    override suspend fun signUp(email: String, password: String): Result<SignUpModel> {
        return signUpDataSource.signUp(
            SignUpRequest(
                Username = email,
                Password = password
            )
        ).mapCatching { response ->
            response.toSignUpModel()
        }
    }

    override suspend fun tokenSignIn(): Result<TokenModel> {
        return tokenSignInDataSource.tokenSignIn()
            .mapCatching { response ->
                response.toTokenModel()
            }
    }

    override suspend fun socialSignIn(accountType: Int, OauthId: String): Result<SocialSignInModel> {
        return socialSignInDataSource.signIn(
            SocialSignInRequest(accountType, OauthId)
        ).mapCatching { response ->
            response.toSocialSignInModel()
        }
    }
}
