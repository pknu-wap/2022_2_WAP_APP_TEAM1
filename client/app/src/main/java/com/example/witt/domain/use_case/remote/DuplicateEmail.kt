package com.example.witt.domain.use_case.remote

import com.example.witt.domain.model.user.DuplicateEmailModel
import com.example.witt.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DuplicateEmail @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(email: String) : Result<DuplicateEmailModel>  =
        userRepository.duplicateEmail(email)
}