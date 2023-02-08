package com.example.witt.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.witt.BuildConfig
import com.example.witt.data.api.DetailPlanService
import com.example.witt.data.api.PlanService
import com.example.witt.data.api.SignInService
import com.example.witt.data.api.SignUpService
import com.example.witt.data.api.UserService
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
            .baseUrl(BuildConfig.host_domain)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSignInService(
        retrofit: Retrofit
    ): SignInService =
        retrofit.create(SignInService::class.java)

    @Provides
    @Singleton
    fun providesSignUpService(
        retrofit: Retrofit
    ): SignUpService = retrofit.create(SignUpService::class.java)

    @Provides
    @Singleton
    fun providesUserService(
        retrofit: Retrofit
    ): UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideMakePlanService(
        retrofit: Retrofit
    ): PlanService = retrofit.create(PlanService::class.java)

    @Provides
    @Singleton
    fun provideDetailPlanService(
        retrofit: Retrofit
    ): DetailPlanService = retrofit.create(DetailPlanService::class.java)
}
