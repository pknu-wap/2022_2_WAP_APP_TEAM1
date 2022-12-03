package com.example.witt.data.model.remote.detail_plan.place.request

data class AddPlaceRequest(
    val PlaceId: String,
    val Latitude: String,
    val Longitude: String,
    val Category: String,
    val RoadAddress: String,
    val Name : String
)