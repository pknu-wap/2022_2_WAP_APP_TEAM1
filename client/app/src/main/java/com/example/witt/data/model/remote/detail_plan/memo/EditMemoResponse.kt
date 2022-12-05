package com.example.witt.data.model.remote.detail_plan.memo

import com.example.witt.domain.model.remote.detail_plan.memo.EditMemoModel

data class EditMemoResponse(
    val status: Boolean,
    val reason: String
)

fun EditMemoResponse.toEditMemoModel() = EditMemoModel(
    status = status,
    reason = reason
)
