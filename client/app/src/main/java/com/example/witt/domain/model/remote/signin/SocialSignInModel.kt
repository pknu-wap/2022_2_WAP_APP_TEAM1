package com.example.witt.domain.model.remote.signin

data class SocialSignInModel (
        val status: Boolean,
        val reason: String,
        val isProfileExists: Boolean
)