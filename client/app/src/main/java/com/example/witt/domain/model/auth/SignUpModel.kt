package com.example.witt.domain.model.auth

import java.security.cert.CertPathValidatorException

data class SignUpModel (
    val status: Boolean,
    val reason: String
        )