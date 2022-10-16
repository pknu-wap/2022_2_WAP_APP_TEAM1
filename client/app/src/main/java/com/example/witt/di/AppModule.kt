package com.example.witt.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.witt.BuildConfig
import com.example.witt.data.api.SignInService
import com.example.witt.data.api.DuplicateEmailService
import com.example.witt.data.api.SignUpService
import com.example.witt.data.api.UserTokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.apiUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSignInService(
        retrofit: Retrofit
    ): SignInService
        = retrofit.create(SignInService::class.java)

    @Provides
    @Singleton
    fun providesSignUpService(
        retrofit: Retrofit
    ): SignUpService = retrofit.create(SignUpService::class.java)

    @Provides
    @Singleton
    fun providesDuplicateEmailService(
        retrofit: Retrofit
    ): DuplicateEmailService = retrofit.create(DuplicateEmailService::class.java)

    @Provides
    @Singleton
    fun providesUserTokenService(
        retrofit: Retrofit
    ): UserTokenService = retrofit.create(UserTokenService::class.java)


    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }

}