package com.example.witt.di

import android.content.SharedPreferences
import com.example.witt.data.auth.AuthApi
import com.example.witt.data.repository.AuthRepositoryImpl
import com.example.witt.domain.repository.AuthRepository
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
    fun provideAuthRepository(api: AuthApi, prefs: SharedPreferences): AuthRepository{
        return AuthRepositoryImpl(api, prefs)
    }

}