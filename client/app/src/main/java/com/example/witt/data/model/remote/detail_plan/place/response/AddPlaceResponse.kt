package com.example.witt.data.model.remote.detail_plan.place.response

import com.example.witt.domain.model.remote.detail_plan.place.AddPlaceResponseModel

data class AddPlaceResponse (
    val status: Boolean,
    val reason: String
        )

fun AddPlaceResponse.toAddPlaceResponseModel() = AddPlaceResponseModel(
    status = status,
    reason = reason
)