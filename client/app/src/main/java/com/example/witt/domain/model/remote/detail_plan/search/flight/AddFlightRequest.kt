package com.example.witt.domain.model.remote.detail_plan.search.flight

data class AddFlightRequest (
    val AirlineCode :String,
    val FlightNum:Int,
    val DepartureTime:String,
    val ArrivalTime:String,
    val DepartureAirport:String,
    val ArrivalAirport:String
)