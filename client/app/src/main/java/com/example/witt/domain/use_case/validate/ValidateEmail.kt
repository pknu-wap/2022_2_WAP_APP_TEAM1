package com.example.witt.domain.use_case.validate

import android.util.Patterns
import com.example.witt.domain.model.validate.ValidationResult

class ValidateEmail {
    fun execute(email: String): ValidationResult {
        if(email.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "이메일을 입력해주세요."
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                successful = false,
                errorMessage = "이메일 형식을 확인해주세요."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}