package com.example.witt.domain.model.remote.detail_plan.place

import com.example.witt.data.model.remote.detail_plan.place.request.AddPlaceRequest

data class AddPlaceModel (
    val placeId: String,
    val latitude: String,
    val longitude: String,
    val category: String,
    val roadAddress: String,
    val name : String
        )

fun AddPlaceModel.toAddPlaceRequest() = AddPlaceRequest(
    PlaceId = placeId,
    Latitude = latitude,
    Longitude = longitude,
    Category = category,
    RoadAddress = roadAddress,
    Name = name
)