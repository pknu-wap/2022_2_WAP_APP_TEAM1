package com.example.witt.data.source.remote.detail_plan.memo

import com.example.witt.data.api.DetailPlanService
import com.example.witt.data.model.remote.detail_plan.memo.MakeMemoResponse
import com.example.witt.data.model.remote.detail_plan.place.request.MemoRequest
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MakeMemoDataSourceImpl @Inject constructor(
    private val service: DetailPlanService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : MakeMemoDataSource {

    override suspend fun makeMemo(tripId: Int, day: Int, content: String): Result<MakeMemoResponse> =
        withContext(coroutineDispatcher) {
            runCatching {
                service.makeMemo(tripId, day, MemoRequest(content))
            }.onFailure {
                it.printStackTrace()
            }
        }
}
