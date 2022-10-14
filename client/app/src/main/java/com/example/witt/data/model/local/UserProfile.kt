package com.example.witt.data.model.local

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity
data class UserProfile (
    @PrimaryKey(autoGenerate = false)
    val userName: String,
    val profileUri: String
        )