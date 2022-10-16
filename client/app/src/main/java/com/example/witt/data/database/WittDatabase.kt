package com.example.witt.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.witt.data.model.local.UserProfile

@Database(
    entities = [UserProfile::class],
    version = 1,
    exportSchema = false
)
abstract class WittDatabase : RoomDatabase(){

    abstract val profileDao : ProfileDao

}