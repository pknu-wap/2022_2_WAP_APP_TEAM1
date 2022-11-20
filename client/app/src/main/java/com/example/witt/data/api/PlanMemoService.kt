package com.example.witt.data.api

import com.example.witt.data.model.plan.memo.MakeMemoResponse
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface PlanMemoService {

    @PUT("/api/trip/{tripId}/plan/days/{day}/memo")
    suspend fun makeMemo(
        @Path("tripId") planId: Int,
        @Path("day") day: Int,
        @Body Content: String
    ) : MakeMemoResponse

}