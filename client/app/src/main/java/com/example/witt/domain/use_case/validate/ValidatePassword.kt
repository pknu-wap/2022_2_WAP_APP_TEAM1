package com.example.witt.domain.use_case.validate

import com.example.witt.domain.model.validate.ValidationResult

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if(password.length < 8){
            return ValidationResult(
                successful = false,
                errorMessage = "비밀번호는 8글자 이상으로 입력해주세요."
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit()} &&
                password.any{ it.isLetter()}
        if(!containsLettersAndDigits){
            return ValidationResult(
                successful = false,
                errorMessage = "비밀번호는 문자와 숫자의 조합으로 입력해주세요."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}