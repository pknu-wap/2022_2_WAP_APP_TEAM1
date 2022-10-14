package com.example.witt.domain.use_case.validate

import com.example.witt.domain.model.validate.ValidationResult

class ValidateNickName {
    fun execute(nickName: String) : ValidationResult {
        if(nickName.length < 4){
            return ValidationResult(
                successful = false,
                errorMessage = "닉네임을 4글자 이상으로 입력해주세요."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}