package com.example.witt.domain.repository

import com.example.witt.domain.model.auth.SignInModel
import com.example.witt.domain.model.auth.SignUpModel

interface AuthRepository {

    suspend fun signIn(accountType: Int, email: String, password: String): Result<SignInModel>

    suspend fun signUp(email: String, password: String): Result<SignUpModel>

}