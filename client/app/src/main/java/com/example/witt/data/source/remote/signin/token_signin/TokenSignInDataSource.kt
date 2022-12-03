package com.example.witt.data.source.remote.signin.token_signin

import com.example.witt.data.model.remote.user.response.TokenResponse


interface TokenSignInDataSource {

    suspend fun tokenSignIn() : Result<TokenResponse>
}