package com.example.witt.domain.model.remote.signup

data class DuplicateEmailModel (
    val status : Boolean,
    val reason: String
        )