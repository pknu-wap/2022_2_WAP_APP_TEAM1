package com.example.witt.data.model.plan.detail_plan.request

data class AddPlaceRequest(
    val PlaceId: String,
    val Latitude: String,
    val Longitude: String,
    val Category: String,
    val RoadAddress: String,
    val Name : String
)