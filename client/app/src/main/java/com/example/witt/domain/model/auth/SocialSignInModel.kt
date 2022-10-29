package com.example.witt.domain.model.auth

data class SocialSignInModel (
        val status: Boolean,
        val reason: String,
        val isProfileExists: Boolean
)