package com.example.witt.domain.use_case.validate

import com.example.witt.domain.model.use_case.validate.ValidationResult

class ValidateNickName {
    fun execute(nickName: String): ValidationResult {
        if (nickName.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = "닉네임은 2글자 이상으로 입력해주세요."
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}
