package com.example.witt.data.source.remote.plan.make_plan

import com.example.witt.data.model.remote.plan.make_plan.request.MakePlanRequest
import com.example.witt.data.model.remote.plan.make_plan.response.MakePlanResponse

interface MakePlanDataSource {

    suspend fun makePlan(makePlanRequest: MakePlanRequest): Result<MakePlanResponse>

}