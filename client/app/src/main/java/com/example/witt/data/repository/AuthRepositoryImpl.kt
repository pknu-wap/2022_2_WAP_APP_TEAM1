package com.example.witt.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.witt.data.auth.AuthApi
import com.example.witt.data.auth.request.SignInRequest
import com.example.witt.data.auth.request.SignUpRequest
import com.example.witt.data.auth.response.SignInResponse
import com.example.witt.data.auth.response.SignUpResponse
import com.example.witt.data.auth.response.toSignInModel
import com.example.witt.data.auth.response.toSignUpModel
import com.example.witt.domain.model.SignInModel
import com.example.witt.domain.model.SignUpModel
import com.example.witt.domain.repository.AuthRepository

class AuthRepositoryImpl (
    private val api: AuthApi,
    private val prefs: SharedPreferences
    ): AuthRepository{

    override suspend fun signIn(accountType: Int, email: String, password: String): Result<SignInModel> {
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
            Result.success(response.toSignInModel())

        } catch (e: Exception){
            e.printStackTrace()
            return Result.failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String): Result<SignUpModel> {
        return try{
            val response = api.signUp(
                request = SignUpRequest(
                    Username =  email,
                    Password = password
                )
            )
            Result.success(response.toSignUpModel())
        }catch (e: Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }
}