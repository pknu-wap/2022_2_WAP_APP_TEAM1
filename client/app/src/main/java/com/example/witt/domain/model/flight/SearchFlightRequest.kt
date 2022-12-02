package com.example.witt.domain.model.flight

data class SearchFlightRequest (
    val flightDate: String,
    val airlineCode : String,
    val flightNum : String
)