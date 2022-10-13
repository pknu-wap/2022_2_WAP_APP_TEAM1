package com.example.witt.domain.use_case.remote

import com.example.witt.domain.model.SignUpModel
import com.example.witt.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpEmailPassword @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String) : Result<SignUpModel> =
        authRepository.signUp(email, password)

}