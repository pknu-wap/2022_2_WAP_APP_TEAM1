package com.example.witt.data.source.remote.memo

import com.example.witt.data.api.DetailPlanService
import com.example.witt.data.model.plan.detail_plan.request.MemoRequest
import com.example.witt.data.model.plan.memo.EditMemoResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditMemoDataSourceImpl @Inject constructor(
    private val service: DetailPlanService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : EditMemoDataSource{
    override suspend fun editMemo(tripId: Int, planId: Int, content : String): Result<EditMemoResponse>
    = withContext(coroutineDispatcher){
        runCatching {
            service.editMemo(tripId, planId, MemoRequest(content))
        }.onFailure { e ->
            e.printStackTrace()
        }
    }
}