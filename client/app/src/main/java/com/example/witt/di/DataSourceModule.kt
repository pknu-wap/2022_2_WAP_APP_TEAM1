package com.example.witt.di

import com.example.witt.data.source.remote.duplicate_check.DuplicateEmailDataSource
import com.example.witt.data.source.remote.duplicate_check.DuplicateEmailDataSourceImpl
import com.example.witt.data.source.remote.signin.SignInDataSource
import com.example.witt.data.source.remote.signin.SignInDataSourceImpl
import com.example.witt.data.source.remote.signup.SignUpDataSource
import com.example.witt.data.source.remote.signup.SignUpDataSourceImpl
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

}