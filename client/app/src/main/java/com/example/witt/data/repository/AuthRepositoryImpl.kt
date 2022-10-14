package com.example.witt.data.repository

import android.content.SharedPreferences
import com.example.witt.data.model.auth.request.SignInRequest
import com.example.witt.data.model.auth.request.SignUpRequest
import com.example.witt.data.mapper.toSignInModel
import com.example.witt.data.mapper.toSignUpModel
import com.example.witt.data.source.remote.signin.SignInDataSource
import com.example.witt.data.source.remote.signup.SignUpDataSource
import com.example.witt.domain.model.auth.SignInModel
import com.example.witt.domain.model.auth.SignUpModel
import com.example.witt.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val signInDataSource: SignInDataSource,
    private val signUpDataSource: SignUpDataSource,
    private val prefs: SharedPreferences
    ): AuthRepository{

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
}