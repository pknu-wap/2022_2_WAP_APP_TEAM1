package com.example.witt.data.source.remote.memo

import com.example.witt.data.api.PlanMemoService
import com.example.witt.data.model.plan.memo.EditMemoRequest
import com.example.witt.data.model.plan.memo.EditMemoResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditMemoDataSourceImpl @Inject constructor(
    private val service: PlanMemoService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : EditMemoDataSource{
    override suspend fun editMemo(tripId: Int, request: EditMemoRequest): Result<EditMemoResponse>
    = withContext(coroutineDispatcher){
        runCatching {
            service.editMemo(tripId, request)
        }.onFailure { e ->
            e.printStackTrace()
        }
    }
}