package com.example.witt.domain.repository

import com.example.witt.domain.model.plan.memo.EditMemoModel
import com.example.witt.domain.model.plan.memo.MakeMemoModel

interface DetailPlanRepository {

    suspend fun makeMemo(tripId : Int, day: Int, content: String) : Result<MakeMemoModel>

    suspend fun editMemo(tripId : Int, Content: String, planId: Int): Result<EditMemoModel>

}