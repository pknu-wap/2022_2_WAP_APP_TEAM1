package com.example.witt.domain.model

data class ValidationResult (
    val successful : Boolean,
    val errorMessage: String? = null
)