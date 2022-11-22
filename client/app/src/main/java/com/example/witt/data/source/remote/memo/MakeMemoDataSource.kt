package com.example.witt.data.source.remote.memo

import com.example.witt.data.model.plan.memo.MakeMemoResponse

interface MakeMemoDataSource {

    suspend fun makeMemo(tripId : Int, day: Int, content: String) : Result<MakeMemoResponse>

}