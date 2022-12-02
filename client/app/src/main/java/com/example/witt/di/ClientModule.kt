package com.example.witt.di

import com.example.witt.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClientModule {

    @Provides
    @Singleton
    fun providesChatSocket(): Socket {
        return IO.socket(BuildConfig.host_domain_socket)
    }

}