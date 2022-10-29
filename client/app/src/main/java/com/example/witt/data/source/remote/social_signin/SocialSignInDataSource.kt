package com.example.witt.data.source.remote.social_signin

import com.example.witt.data.model.auth.request.SocialSignInRequest
import com.example.witt.data.model.auth.response.SocialSignInResponse

interface SocialSignInDataSource {
    suspend fun signIn(socialSignInRequest: SocialSignInRequest) : Result<SocialSignInResponse>
}