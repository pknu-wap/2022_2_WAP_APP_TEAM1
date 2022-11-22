package com.example.witt.data.repository

import com.example.witt.data.model.plan.memo.EditMemoRequest
import com.example.witt.data.model.plan.memo.toEditMemoModel
import com.example.witt.data.model.plan.memo.toMakeMemoModel
import com.example.witt.data.source.remote.memo.EditMemoDataSource
import com.example.witt.data.source.remote.memo.MakeMemoDataSource
import com.example.witt.domain.model.plan.memo.EditMemoModel
import com.example.witt.domain.model.plan.memo.MakeMemoModel
import com.example.witt.domain.repository.DetailPlanRepository
import javax.inject.Inject

class DetailPlanRepositoryImpl @Inject constructor(
    private val makeMemoDataSource: MakeMemoDataSource,
    private val editMemoDataSource: EditMemoDataSource
): DetailPlanRepository {

    override suspend fun makeMemo(tripId : Int, day: Int, content: String): Result<MakeMemoModel> {
        return makeMemoDataSource.makeMemo(tripId, day, content).mapCatching { response ->
            response.toMakeMemoModel()
        }
    }

    override suspend fun editMemo(tripId: Int, Content: String, planId: Int, ): Result<EditMemoModel> {
        return editMemoDataSource.editMemo(tripId = tripId, request = EditMemoRequest(Content, planId)
        ).mapCatching { response ->
            response.toEditMemoModel()
        }
    }

}