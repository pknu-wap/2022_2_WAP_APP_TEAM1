package com.example.witt.data.source.remote.detail_plan.memo

import com.example.witt.data.model.remote.detail_plan.memo.MakeMemoResponse

interface MakeMemoDataSource {

    suspend fun makeMemo(tripId : Int, day: Int, content: String) : Result<MakeMemoResponse>

}