package com.example.witt.data.model.plan.detail_plan.response

import com.example.witt.domain.model.plan.detail_plan.AddPlaceResponseModel

data class AddPlaceResponse (
    val status: Boolean,
    val reason: String
        )

fun AddPlaceResponse.toAddPlaceResponseModel() = AddPlaceResponseModel(
    status = status,
    reason = reason
)