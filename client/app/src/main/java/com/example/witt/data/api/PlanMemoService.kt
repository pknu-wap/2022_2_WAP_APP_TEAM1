package com.example.witt.data.api

import com.example.witt.data.model.plan.detail_plan.request.MemoRequest
import com.example.witt.data.model.plan.memo.EditMemoResponse
import com.example.witt.data.model.plan.memo.MakeMemoResponse
import retrofit2.http.*

interface PlanMemoService {


    @PUT("/api/trip/{tripId}/plan/days/{day}/memo")
    suspend fun makeMemo(
        @Path("tripId") planId: Int,
        @Path("day") day: Int,
        @Body Content: MemoRequest
    ) : MakeMemoResponse

    @PATCH("/api/trip/{tripId}/plan/{planId}/memo")
    suspend fun editMemo(
        @Path("tripId") tripId: Int,
        @Path("planId") planId: Int,
        @Body Content: MemoRequest
    ): EditMemoResponse

}