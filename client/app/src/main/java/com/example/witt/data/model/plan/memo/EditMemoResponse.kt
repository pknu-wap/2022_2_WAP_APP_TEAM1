package com.example.witt.data.model.plan.memo

import com.example.witt.domain.model.plan.memo.EditMemoModel


data class EditMemoResponse (
    val status: Boolean,
    val reason: String
    )

fun EditMemoResponse.toEditMemoModel() = EditMemoModel(
    status = status,
    reason = reason
)