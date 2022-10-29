package com.example.witt.data.model.auth.response

data class SocialSignInResponse(
    val status : Boolean,
    val reason: String,
    val AccessToken : String,
    val RefreshToken : String,
    val isProfileExists: Boolean
)