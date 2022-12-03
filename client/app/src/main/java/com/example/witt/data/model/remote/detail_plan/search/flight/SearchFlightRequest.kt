package com.example.witt.data.model.remote.detail_plan.search.flight

data class SearchFlightRequest (
    val flightDate:String,
    val airlineCode : String,
    val flightNum : String
)