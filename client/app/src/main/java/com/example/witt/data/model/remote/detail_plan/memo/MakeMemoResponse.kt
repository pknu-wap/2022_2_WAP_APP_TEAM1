package com.example.witt.data.model.remote.detail_plan.memo

import com.example.witt.domain.model.remote.detail_plan.memo.MakeMemoModel

data class MakeMemoResponse(
    val status: Boolean,
    val reason: String
)
fun MakeMemoResponse.toMakeMemoModel() = MakeMemoModel(
    status = status,
    reason = reason
)
