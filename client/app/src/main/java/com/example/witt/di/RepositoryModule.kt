package com.example.witt.di

import android.content.SharedPreferences
import com.example.witt.data.repository.AuthRepositoryImpl
import com.example.witt.data.repository.UserRepositoryImpl
import com.example.witt.data.source.local.user_profile.ProfileDataSource
import com.example.witt.data.source.remote.duplicate_check.DuplicateEmailDataSource
import com.example.witt.data.source.remote.profile.ProfileUploadDataSource
import com.example.witt.data.source.remote.signin.SignInDataSource
import com.example.witt.data.source.remote.signup.SignUpDataSource
import com.example.witt.data.source.remote.user.UserTokenDataSource
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
    fun provideAuthRepository(signInDataSource : SignInDataSource,
                              signUpDataSource: SignUpDataSource,
                              prefs: SharedPreferences): AuthRepository{
        return AuthRepositoryImpl(signInDataSource, signUpDataSource, prefs)
    }

    @Provides
    @Singleton
    fun provideUserRepository(duplicateEmailDataSource: DuplicateEmailDataSource,
                              userTokenDataSource: UserTokenDataSource,
                              profileDataSource: ProfileDataSource,
                              profileUploadDataSource: ProfileUploadDataSource
    ): UserRepository{
        return UserRepositoryImpl(duplicateEmailDataSource, userTokenDataSource, profileDataSource,profileUploadDataSource)
    }
}