package com.example.witt.domain.use_case.local

import com.example.witt.domain.repository.UserRepository
import javax.inject.Inject


class SetLocalProfile @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(profileUri: String, userName: String) : Result<Unit> =
        userRepository.setProfile(profileUri, userName)
}