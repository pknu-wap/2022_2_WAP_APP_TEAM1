package com.example.witt.domain.model.remote.signin

data class SignInModel (
    val status: Boolean,
    val reason: String,
    val isProfileExists: Boolean
)