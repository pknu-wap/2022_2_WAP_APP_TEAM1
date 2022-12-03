package com.example.witt.data.api

import com.example.witt.data.model.remote.detail_plan.place.request.AddPlaceRequest
import com.example.witt.data.model.remote.detail_plan.place.request.MemoRequest
import com.example.witt.data.model.remote.detail_plan.place.response.AddPlaceResponse
import com.example.witt.data.model.remote.detail_plan.memo.EditMemoResponse
import com.example.witt.data.model.remote.detail_plan.memo.MakeMemoResponse
import com.example.witt.data.model.remote.detail_plan.search.ResultSearchKeyword
import com.example.witt.data.model.remote.detail_plan.search.flight.AddFlightResponse
import com.example.witt.data.model.remote.detail_plan.search.flight.SearchFlightResponse
import com.example.witt.domain.model.remote.detail_plan.search.flight.AddFlightRequest
import retrofit2.Call
import retrofit2.http.*

interface DetailPlanService {

    @PUT("/api/trip/{tripId}/plan/days/{dayId}/place")
    suspend fun addPlace(
        @Path ("tripId") tripId: Int,
        @Path ("dayId") dayId: Int,
        @Body request: AddPlaceRequest
    ): AddPlaceResponse

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

    @GET("v2/local/search/keyword.json")
    fun getSearchKeyword(
        @Header("Authorization") key : String,
        @Query("query") query: String
    ): Call<ResultSearchKeyword>

    @GET("/api/flight/flightNumber")
    suspend fun searchFlight(
        @Query("flightDate") flightDate: String,
        @Query("airlineCode") airlineCode:String,
        @Query("flightNum") flightNum:String
    ): SearchFlightResponse

    @PUT("/api/trip/{tripId}/plan/flight")
    suspend fun addFlight(
        @Path("tripId") tripId:Int,
        @Body request: AddFlightRequest
    ): AddFlightResponse

}