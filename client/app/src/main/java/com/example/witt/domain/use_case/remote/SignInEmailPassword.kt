package com.example.witt.domain.use_case.remote

import com.example.witt.domain.model.SignInModel
import com.example.witt.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInEmailPassword @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(accountType: Int, email: String, password: String) : Result<SignInModel> = withContext(Dispatchers.IO){
        authRepository.signIn(accountType, email, password)
    }
}