package com.example.witt.domain.use_case.remote

import com.example.witt.domain.model.remote.signin.TokenModel
import com.example.witt.domain.repository.AuthRepository
import javax.inject.Inject

class TokenSignIn @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<TokenModel> = authRepository.tokenSignIn()
}