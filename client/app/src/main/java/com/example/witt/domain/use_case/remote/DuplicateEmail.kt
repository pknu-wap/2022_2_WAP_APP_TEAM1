package com.example.witt.domain.use_case.remote

import com.example.witt.domain.model.remote.signup.DuplicateEmailModel
import com.example.witt.domain.repository.UserRepository
import javax.inject.Inject

class DuplicateEmail @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(email: String) : Result<DuplicateEmailModel>  =
        userRepository.duplicateEmail(email)
}