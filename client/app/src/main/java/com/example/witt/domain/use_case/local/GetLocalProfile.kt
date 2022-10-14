package com.example.witt.domain.use_case.local

import com.example.witt.domain.model.user.UserProfileModel
import com.example.witt.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalProfile @Inject constructor(
    private val userRepository: UserRepository
){
    operator fun invoke() : Flow<Result<UserProfileModel>> =
        userRepository.getProfile()
}