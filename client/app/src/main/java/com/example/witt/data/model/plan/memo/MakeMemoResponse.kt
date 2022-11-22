package com.example.witt.data.model.plan.memo

import com.example.witt.domain.model.plan.memo.MakeMemoModel

data class MakeMemoResponse (
    val status: Boolean,
    val reason: String
)
fun MakeMemoResponse.toMakeMemoModel() = MakeMemoModel(
    status = status,
    reason = reason
)