package com.example.witt.data.source.remote.memo

import com.example.witt.data.model.plan.memo.EditMemoRequest
import com.example.witt.data.model.plan.memo.EditMemoResponse

interface EditMemoDataSource {

    suspend fun editMemo(tripId: Int, request: EditMemoRequest) : Result<EditMemoResponse>

}