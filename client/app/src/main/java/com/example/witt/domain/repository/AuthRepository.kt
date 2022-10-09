package com.example.witt.domain.repository

import com.example.witt.data.auth.response.SignInResponse

interface AuthRepository {

    suspend fun signIn(accountType: Int, email: String, password: String): Result<SignInResponse>

}