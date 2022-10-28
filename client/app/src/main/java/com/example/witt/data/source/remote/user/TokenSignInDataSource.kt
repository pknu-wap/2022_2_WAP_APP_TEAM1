package com.example.witt.data.source.remote.token_signin

import com.example.witt.data.model.user.response.TokenResponse


interface TokenSignInDataSource {

    suspend fun tokenSignIn() : Result<TokenResponse>
}