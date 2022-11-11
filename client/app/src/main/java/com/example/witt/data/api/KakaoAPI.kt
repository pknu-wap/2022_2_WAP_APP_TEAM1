package com.example.witt.data.api


import com.example.witt.presentation.ui.plan.drawup_plan.adapter.ResultSearchKeyword
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoAPI {
    @GET("v2/local/search/keyword.json")
    fun getSearchKeyword(
        @Header("Authorization") key : String,
        @Query("query") query: String
    ): Call<ResultSearchKeyword>
}