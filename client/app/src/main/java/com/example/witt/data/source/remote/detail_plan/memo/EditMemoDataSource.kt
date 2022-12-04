package com.example.witt.data.source.remote.detail_plan.memo

import com.example.witt.data.model.remote.detail_plan.memo.EditMemoResponse

interface EditMemoDataSource {

    suspend fun editMemo(tripId: Int, planId: Int, content: String): Result<EditMemoResponse>
}
