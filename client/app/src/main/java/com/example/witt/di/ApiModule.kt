package com.example.witt.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.witt.BuildConfig
import com.example.witt.data.api.*
import com.example.witt.data.api.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor = authInterceptor

    @Singleton
    @Provides
    fun provideOkHttpInterceptor(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
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
    fun providesProfileUploadService(
        retrofit: Retrofit
    ):ProfileUploadService = retrofit.create(ProfileUploadService::class.java)


    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }

}