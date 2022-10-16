package com.example.witt.domain.use_case.remote

import com.example.witt.domain.model.auth.SignUpModel
import com.example.witt.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpEmailPassword @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String) : Result<SignUpModel> =
        authRepository.signUp(email, password)

}