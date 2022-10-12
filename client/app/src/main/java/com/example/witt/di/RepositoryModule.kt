package com.example.witt.di

import android.content.SharedPreferences
import com.example.witt.data.api.DefaultApiService
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
    fun provideAuthRepository(api: DefaultApiService, prefs: SharedPreferences): AuthRepository{
        return AuthRepositoryImpl(api, prefs)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: DefaultApiService): UserRepository{
        return UserRepositoryImpl(api)
    }
}