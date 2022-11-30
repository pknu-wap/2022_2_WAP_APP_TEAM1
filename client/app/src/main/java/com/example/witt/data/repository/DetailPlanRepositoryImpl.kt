package com.example.witt.data.repository

import com.example.witt.data.model.plan.detail_plan.response.toAddPlaceResponseModel
import com.example.witt.data.model.plan.memo.toEditMemoModel
import com.example.witt.data.model.plan.memo.toMakeMemoModel
import com.example.witt.data.source.remote.memo.EditMemoDataSource
import com.example.witt.data.source.remote.memo.MakeMemoDataSource
import com.example.witt.data.source.remote.plan.detail_plan.AddPlaceDataSource
import com.example.witt.domain.model.plan.detail_plan.AddPlaceModel
import com.example.witt.domain.model.plan.detail_plan.AddPlaceResponseModel
import com.example.witt.domain.model.plan.detail_plan.toAddPlaceRequest
import com.example.witt.domain.model.plan.memo.EditMemoModel
import com.example.witt.domain.model.plan.memo.MakeMemoModel
import com.example.witt.domain.repository.DetailPlanRepository
import javax.inject.Inject

class DetailPlanRepositoryImpl @Inject constructor(
    private val makeMemoDataSource: MakeMemoDataSource,
    private val editMemoDataSource: EditMemoDataSource,
    private val addPlaceDataSource: AddPlaceDataSource
): DetailPlanRepository {

    override suspend fun makeMemo(tripId : Int, day: Int, content: String): Result<MakeMemoModel> {
        return makeMemoDataSource.makeMemo(tripId, day, content).mapCatching { response ->
            response.toMakeMemoModel()
        }
    }

    override suspend fun editMemo(tripId: Int, planId: Int, Content: String): Result<EditMemoModel> {
        return editMemoDataSource.editMemo(tripId = tripId, planId = planId, Content)
            .mapCatching { response ->
                response.toEditMemoModel()
        }
    }

    override suspend fun addPlace(tripId: Int, day: Int, content: AddPlaceModel)
    : Result<AddPlaceResponseModel> {
        return addPlaceDataSource.addPlace(tripId, day, content.toAddPlaceRequest())
            .mapCatching { response ->
                response.toAddPlaceResponseModel()
            }
    }
}