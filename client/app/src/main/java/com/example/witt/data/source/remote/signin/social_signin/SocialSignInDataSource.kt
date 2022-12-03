package com.example.witt.data.source.remote.signin.social_signin

import com.example.witt.data.model.remote.signin.request.SocialSignInRequest
import com.example.witt.data.model.remote.signin.response.SocialSignInResponse

interface SocialSignInDataSource {
    suspend fun signIn(socialSignInRequest: SocialSignInRequest) : Result<SocialSignInResponse>
}