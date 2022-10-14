package com.example.witt.domain.use_case.validate

import com.example.witt.domain.model.validate.ValidationResult

class ValidateRepeatedPassword {
    fun execute(password: String, repeatedPassword : String): ValidationResult {
        if(repeatedPassword != password){
            return ValidationResult(
                successful = false,
                errorMessage = "비밀번호가 일치하지 않습니다."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}