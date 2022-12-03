package com.example.witt.domain.model.remote.detail_plan.search

import com.example.witt.data.model.remote.detail_plan.search.flight.Flight
import com.example.witt.domain.model.remote.detail_plan.search.flight.AddFlightRequest


data class SearchFlightModel(
    val Status:Boolean,
    val Result:String,
    val Flight: Flight
)

data class Flight(
    val airline :String,
    val flightNo:Int,
    val flyFrom:String,
    val flyTo:String,
    val departure:String,
    val arrival:String
)

fun Flight.toAddFlightRequest() = AddFlightRequest(
    AirlineCode = airline,
    FlightNum = flightNo,
    DepartureAirport = flyFrom,
    ArrivalAirport =  flyTo,
    DepartureTime = departure,
    ArrivalTime = arrival
)