package com.example.witt.domain.model.remote.detail_plan.search

data class SearchFlightRequest (
    val flightDate: String,
    val airlineCode : String,
    val flightNum : String
)