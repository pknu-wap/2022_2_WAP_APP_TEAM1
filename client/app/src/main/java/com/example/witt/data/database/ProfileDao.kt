package com.example.witt.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.witt.data.model.local.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setProfile(userProfile: UserProfile)

    @Query("SELECT * FROM userProfile")
    fun getProfile(): Flow<UserProfile>

}