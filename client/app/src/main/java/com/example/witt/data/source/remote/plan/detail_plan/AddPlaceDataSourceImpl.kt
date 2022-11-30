package com.example.witt.data.source.remote.plan.detail_plan

import com.example.witt.data.api.DetailPlanService
import com.example.witt.data.model.plan.detail_plan.request.AddPlaceRequest
import com.example.witt.data.model.plan.detail_plan.response.AddPlaceResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddPlaceDataSourceImpl @Inject constructor(
    private val service : DetailPlanService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): AddPlaceDataSource{

    override suspend fun addPlace(tripId: Int, dayId: Int, request: AddPlaceRequest): Result<AddPlaceResponse>
    = withContext(coroutineDispatcher){
        runCatching {
            service.addPlace(tripId, dayId, request)
        }.onFailure { e->
            e.printStackTrace()
        }
    }
}