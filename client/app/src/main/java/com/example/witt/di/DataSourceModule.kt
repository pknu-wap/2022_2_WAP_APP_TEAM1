package com.example.witt.di

import com.example.witt.data.source.local.user_profile.ProfileDataSource
import com.example.witt.data.source.local.user_profile.ProfileDataSourceImpl
import com.example.witt.data.source.remote.duplicate_check.DuplicateEmailDataSource
import com.example.witt.data.source.remote.duplicate_check.DuplicateEmailDataSourceImpl
import com.example.witt.data.source.remote.profile.ProfileUploadDataSource
import com.example.witt.data.source.remote.profile.ProfileUploadDataSourceImpl
import com.example.witt.data.source.remote.signin.SignInDataSource
import com.example.witt.data.source.remote.signin.SignInDataSourceImpl
import com.example.witt.data.source.remote.signup.SignUpDataSource
import com.example.witt.data.source.remote.signup.SignUpDataSourceImpl
import com.example.witt.data.source.remote.social_signin.SocialSignInDataSource
import com.example.witt.data.source.remote.social_signin.SocialSignInDataSourceImpl
import com.example.witt.data.source.remote.token_signin.TokenSignInDataSource
import com.example.witt.data.source.remote.token_signin.TokenSignInDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun provideSignUpDataSource(
        signUpDataSourceImpl: SignUpDataSourceImpl
    ): SignUpDataSource

    @Binds
    @Singleton
    abstract fun provideSignInDataSource(
        signInDataSourceImpl: SignInDataSourceImpl
    ): SignInDataSource


    @Binds
    @Singleton
    abstract fun provideDuplicateEmailDataSource(
        duplicateEmailDataSourceImpl: DuplicateEmailDataSourceImpl
    ): DuplicateEmailDataSource

    @Binds
    @Singleton
    abstract fun provideTokenDataSource(
        TokenDataSourceImpl: TokenSignInDataSourceImpl
    ): TokenSignInDataSource

    @Binds
    @Singleton
    abstract fun provideProfileDataSource(
        profileDataSourceImpl: ProfileDataSourceImpl
    ): ProfileDataSource

    @Binds
    @Singleton
    abstract fun provideProfileUploadDataSource(
        profileUploadDataSourceImpl: ProfileUploadDataSourceImpl
    ): ProfileUploadDataSource

    @Binds
    @Singleton
    abstract fun provideSocialSignInDataSource(
        socialSignInDataSourceImpl: SocialSignInDataSourceImpl
    ): SocialSignInDataSource
}