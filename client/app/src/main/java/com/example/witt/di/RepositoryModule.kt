package com.example.witt.di

import android.content.SharedPreferences
import com.example.witt.data.api.SignInService
import com.example.witt.data.api.DuplicateEmailService
import com.example.witt.data.api.SignUpService
import com.example.witt.data.repository.AuthRepositoryImpl
import com.example.witt.data.repository.UserRepositoryImpl
import com.example.witt.domain.repository.AuthRepository
import com.example.witt.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(signInService: SignInService, signUpService: SignUpService, prefs: SharedPreferences): AuthRepository{
        return AuthRepositoryImpl(signInService, signUpService, prefs)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: DuplicateEmailService): UserRepository{
        return UserRepositoryImpl(api)
    }
}