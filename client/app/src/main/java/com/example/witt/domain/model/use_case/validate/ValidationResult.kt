package com.example.witt.domain.model.use_case.validate

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
