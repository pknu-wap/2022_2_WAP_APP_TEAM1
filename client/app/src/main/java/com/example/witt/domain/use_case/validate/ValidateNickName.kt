package com.example.witt.domain.use_case.validate

import com.example.witt.domain.model.validate.ValidationResult

class ValidateNickName {
    fun execute(nickName: String) : ValidationResult {
        val containsLettersAndDigits = nickName.any { it.isDigit()} &&
                nickName.any{ it.isLetter()}
        if(!containsLettersAndDigits){
            return ValidationResult(
                successful = false,
                errorMessage = "닉네임은 숫자와 문자로만 구성해주세요."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}