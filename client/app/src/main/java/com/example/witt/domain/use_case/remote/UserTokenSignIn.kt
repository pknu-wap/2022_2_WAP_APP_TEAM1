package com.example.witt.domain.use_case.remote

import com.example.witt.domain.model.user.UserTokenModel
import com.example.witt.domain.repository.UserRepository
import javax.inject.Inject

class UserTokenSignIn @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String) : Result<UserTokenModel>
    = userRepository.userTokenSignIn(accessToken, refreshToken)
}