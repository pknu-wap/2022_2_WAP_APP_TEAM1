package com.example.witt.domain.model.plan.detail_plan

import com.example.witt.data.model.plan.detail_plan.request.AddPlaceRequest

data class AddPlaceModel (
    val placeId: String,
    val latitude: String,
    val longitude: String,
    val category: String,
    val administrationCode: Int,
    val name : String
        )

fun AddPlaceModel.toAddPlaceRequest() = AddPlaceRequest(
    PlaceId = placeId,
    Latitude = latitude,
    Longitude = longitude,
    Category = category,
    AdministrationCode = administrationCode,
    Name = name
)