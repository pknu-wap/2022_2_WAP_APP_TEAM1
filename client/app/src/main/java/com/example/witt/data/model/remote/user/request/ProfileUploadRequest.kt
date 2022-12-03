package com.example.witt.data.model.remote.user.request

import java.io.File

data class ProfileUploadRequest (
    val profile: File,
    val Nickname: String,
    val PhoneNum: String
        )