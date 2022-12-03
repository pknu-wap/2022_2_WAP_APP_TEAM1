package com.example.witt.domain.repository

import com.example.witt.domain.model.remote.signin.SignInModel
import com.example.witt.domain.model.remote.signup.SignUpModel
import com.example.witt.domain.model.remote.signin.SocialSignInModel
import com.example.witt.domain.model.remote.signin.TokenModel

interface AuthRepository {

    suspend fun signIn(accountType: Int, email: String, password: String): Result<SignInModel>

    suspend fun signUp(email: String, password: String): Result<SignUpModel>

    suspend fun tokenSignIn(): Result<TokenModel>

    suspend fun socialSignIn(accountType: Int, OauthId: String): Result<SocialSignInModel>

}