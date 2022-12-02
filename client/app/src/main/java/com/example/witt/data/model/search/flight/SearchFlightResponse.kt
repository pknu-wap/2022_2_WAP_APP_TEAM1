package com.example.witt.data.model.search.flight

import com.example.witt.domain.model.flight.SearchFlightModel


data class SearchFlightResponse (
    val status:Boolean,
    val result:String,
    val flight:Flight
        )

data class Flight(
    val airline :String,
    val flightNo:Int,
    val flyFrom:String,
    val flyTo:String,
    val departure:String,
    val arrival:String
)

fun SearchFlightResponse.toAddFlightResponse() = SearchFlightModel(
    Status = status,
    Result = result,
    Flight = flight
)