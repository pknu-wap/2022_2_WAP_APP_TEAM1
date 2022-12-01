package com.example.witt.data.model.search.flight

data class AddFlightRequest (
    val AirlineCode :String,
    val FlightNum:Int,
    val DepartureTime:String,
    val ArrivalTime:String,
    val DepartureAirport:String,
    val ArrivalAirport:String
)