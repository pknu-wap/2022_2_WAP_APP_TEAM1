package com.example.witt.domain.repository

import com.example.witt.domain.model.remote.detail_plan.memo.EditMemoModel
import com.example.witt.domain.model.remote.detail_plan.memo.MakeMemoModel
import com.example.witt.domain.model.remote.detail_plan.place.AddPlaceModel
import com.example.witt.domain.model.remote.detail_plan.place.AddPlaceResponseModel

interface DetailPlanRepository {

    suspend fun makeMemo(tripId: Int, day: Int, content: String): Result<MakeMemoModel>

    suspend fun editMemo(tripId: Int, planId: Int, Content: String): Result<EditMemoModel>

    suspend fun addPlace(tripId: Int, day: Int, content: AddPlaceModel): Result<AddPlaceResponseModel>
}
