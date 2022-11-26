package com.example.witt.data.source.remote.memo

import com.example.witt.data.model.plan.memo.EditMemoResponse

interface EditMemoDataSource {

    suspend fun editMemo(tripId: Int, planId: Int,  content : String) : Result<EditMemoResponse>

}