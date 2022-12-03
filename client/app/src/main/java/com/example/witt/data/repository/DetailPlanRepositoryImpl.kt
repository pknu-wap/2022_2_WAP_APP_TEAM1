package com.example.witt.data.repository

import com.example.witt.data.model.remote.detail_plan.place.response.toAddPlaceResponseModel
import com.example.witt.data.model.remote.detail_plan.memo.toEditMemoModel
import com.example.witt.data.model.remote.detail_plan.memo.toMakeMemoModel
import com.example.witt.data.source.remote.detail_plan.memo.EditMemoDataSource
import com.example.witt.data.source.remote.detail_plan.memo.MakeMemoDataSource
import com.example.witt.data.source.remote.detail_plan.place.AddPlaceDataSource
import com.example.witt.domain.model.remote.detail_plan.place.AddPlaceModel
import com.example.witt.domain.model.remote.detail_plan.place.AddPlaceResponseModel
import com.example.witt.domain.model.remote.detail_plan.place.toAddPlaceRequest
import com.example.witt.domain.model.remote.detail_plan.memo.EditMemoModel
import com.example.witt.domain.model.remote.detail_plan.memo.MakeMemoModel
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