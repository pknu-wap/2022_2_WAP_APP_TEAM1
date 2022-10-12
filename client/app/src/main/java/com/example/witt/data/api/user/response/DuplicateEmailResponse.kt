package com.example.witt.data.api.user.response

import com.example.witt.domain.model.user.DuplicateEmailModel

data class DuplicateEmailResponse (
    val status: Boolean,
    val reason: String
        )

fun DuplicateEmailResponse.toDuplicateEmailModel() =
    DuplicateEmailModel(
        status = status,
        reason = reason
    )