package com.example.witt.domain.use_case.remote

import com.example.witt.domain.model.auth.SignInModel
import com.example.witt.domain.repository.AuthRepository
import javax.inject.Inject

class SignInEmailPassword @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(accountType: Int, email: String, password: String) : Result<SignInModel> =
        authRepository.signIn(accountType, email, password)
}