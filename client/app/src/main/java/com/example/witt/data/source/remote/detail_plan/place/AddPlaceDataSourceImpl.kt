package com.example.witt.data.source.remote.detail_plan.place

import com.example.witt.data.api.DetailPlanService
import com.example.witt.data.model.remote.detail_plan.place.request.AddPlaceRequest
import com.example.witt.data.model.remote.detail_plan.place.response.AddPlaceResponse
import com.example.witt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddPlaceDataSourceImpl @Inject constructor(
    private val service : DetailPlanService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): AddPlaceDataSource {

    override suspend fun addPlace(tripId: Int, dayId: Int, request: AddPlaceRequest): Result<AddPlaceResponse>
    = withContext(coroutineDispatcher){
        runCatching {
            service.addPlace(tripId, dayId, request)
        }.onFailure { e->
            e.printStackTrace()
        }
    }
}