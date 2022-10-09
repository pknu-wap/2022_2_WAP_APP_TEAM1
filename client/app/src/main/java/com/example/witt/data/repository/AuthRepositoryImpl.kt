package com.example.witt.data.repository

import android.content.SharedPreferences
import com.example.witt.data.auth.AuthApi
import com.example.witt.data.auth.request.SignInRequest
import com.example.witt.data.auth.response.SignInResponse
import com.example.witt.domain.repository.AuthRepository

class AuthRepositoryImpl (
    private val api: AuthApi,
    private val prefs: SharedPreferences
    ): AuthRepository{

    override suspend fun signIn(accountType: Int, email: String, password: String): Result<SignInResponse> {
        return try{
            val response = api.signIn(
                request = SignInRequest(
                    AccountType = accountType,
                    Username = email,
                    Password = password
                )
            )
            prefs.edit()
                .putString("accessToken", response.AccessToken)
                .putString("refreshToken", response.RefreshToken)
                .apply()
            Result.success(response)

        } catch (e: Exception){
            e.printStackTrace()
            return Result.failure(e)
        }
    }
}