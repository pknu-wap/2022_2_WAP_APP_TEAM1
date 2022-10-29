package com.example.witt.domain.use_case.remote

import com.example.witt.domain.model.auth.SocialSignInModel
import com.example.witt.domain.repository.AuthRepository
import javax.inject.Inject

class SocialSignIn @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(accountType: Int, OauthId: String): Result<SocialSignInModel>
    = authRepository.socialSignIn(accountType, OauthId)
}