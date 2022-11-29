package com.example.witt.domain.repository

import com.example.witt.domain.model.plan.detail_plan.AddPlaceModel
import com.example.witt.domain.model.plan.detail_plan.AddPlaceResponseModel
import com.example.witt.domain.model.plan.memo.EditMemoModel
import com.example.witt.domain.model.plan.memo.MakeMemoModel

interface DetailPlanRepository {

    suspend fun makeMemo(tripId : Int, day: Int, content: String) : Result<MakeMemoModel>

    suspend fun editMemo(tripId: Int, planId: Int, Content: String): Result<EditMemoModel>

    suspend fun addPlace(tripId: Int, day: Int, content: AddPlaceModel): Result<AddPlaceResponseModel>

}