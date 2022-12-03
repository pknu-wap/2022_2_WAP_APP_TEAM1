package com.example.witt.data.model.remote.detail_plan.search.flight

data class AddFlightResponse (
    val status:Boolean,
    val reason:String,
    val planId:Int,
    val planFlight:PlanFlight
)

data class PlanFlight(
    val TripId :Int,
    val PlanId:Int,
    val AirlineCode:String,
    val FlightNum:String,
    val DepartureTime:String,
    val ArrivalTime:String,
    val DepartureAirport:String
)