package com.example.witt.di

import android.content.Context
import androidx.room.Room
import com.example.witt.data.database.WittDatabase
import com.example.witt.data.database.ProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideWittDatabase(@ApplicationContext context: Context): WittDatabase =
        Room.databaseBuilder(context, WittDatabase::class.java, "witt.db")
            .build()


    @Provides
    @Singleton
    fun provideProfileDao(database: WittDatabase): ProfileDao = database.profileDao

}