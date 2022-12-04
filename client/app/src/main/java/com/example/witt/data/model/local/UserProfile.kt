package com.example.witt.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserProfile(
    @PrimaryKey(autoGenerate = false)
    val userName: String,
    val profileUri: String
)
