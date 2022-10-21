package com.example.witt.domain.use_case.validate

import com.example.witt.domain.model.validate.ValidationResult

class ValidatePhoneNum {

    fun execute(phoneNum: String): ValidationResult {
        if(!phoneNum.any{it.isDigit()}){
            return ValidationResult(
                successful = false,
                errorMessage = "숫자로만 구성해주세요."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}