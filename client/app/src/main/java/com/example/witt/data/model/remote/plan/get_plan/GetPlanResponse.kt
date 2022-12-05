package com.example.witt.data.model.remote.plan.get_plan

import com.example.witt.domain.model.remote.plan.get_plan.DetailPlanModel
import com.example.witt.domain.model.remote.plan.get_plan.GetPlanModel
import com.example.witt.domain.model.remote.plan.get_plan.PlanDataModel
import com.example.witt.domain.model.remote.plan.get_plan.PlanFlightModel
import com.example.witt.domain.model.remote.plan.get_plan.PlanMemoModel
import com.example.witt.domain.model.remote.plan.get_plan.PlanParticipantModel
import com.example.witt.domain.model.remote.plan.get_plan.PlanPlaceModel
import com.example.witt.utils.convertIsoToDate

data class GetPlanResponse(
    val status: Boolean,
    val reason: String,
    val result: PlanDataResponse,
)

data class PlanDataResponse(
    val EndDate: String,
    val Name: String,
    val OwnerId: String,
    val Participants: List<PlanParticipant>?,
    val Plans: List<DetailPlanResponse>,
    val Region: String,
    val StartDate: String,
    val TripId: Int
)

data class DetailPlanResponse(
    val Day: Int,
    val Memo: PlanMemoResponse?,
    val OrderIndex: Int,
    val Place: PlanPlaceResponse?,
    val PlanId: Int,
    val Type: Int,
    val Flight: PlanFlightResponse?
)

data class PlanParticipant(
    val UserId: String,
    val Nickname: String,
    val ProfileImage: String
)

data class PlanMemoResponse(
    val Content: String,
    val Created_At: String,
    val Updated_At: String
)

data class PlanPlaceResponse(
    val Category: String,
    val Latitude: Double,
    val Longitude: Double,
    val Name: String,
    val RoadAddress: String
)

data class PlanFlightResponse(
    val AirlineCode: String,
    val FlightNum: String,
    val DepartureTime: String,
    val ArrivalTime: String,
    val DepartureAirport: String,
    val ArrivalAirport: String
)

fun GetPlanResponse.toGetPlanModel() = GetPlanModel(
    status = status,
    reason = reason,
    data = result.toPlanDataModel()
)

fun PlanDataResponse.toPlanDataModel() = PlanDataModel(
    endDate = EndDate.convertIsoToDate(),
    name = Name,
    ownerId = OwnerId,
    participants = Participants?.map { it.toPlanParticipantsModel() },
    plans = Plans.map { it.toDetailPlanModel() },
    region = Region,
    startDate = StartDate.convertIsoToDate(),
    tripId = TripId,
)

fun PlanParticipant.toPlanParticipantsModel() = PlanParticipantModel(
    userId = UserId,
    nickName = Nickname,
    profileImage = ProfileImage
)

fun DetailPlanResponse.toDetailPlanModel() = DetailPlanModel(
    day = Day,
    memo = Memo?.toPlanMemoModel(),
    orderIndex = OrderIndex,
    place = Place?.toPlanPlaceModel(),
    planId = PlanId,
    type = Type,
    flight = Flight?.toPlanFlightModel()
)

fun PlanMemoResponse.toPlanMemoModel() = PlanMemoModel(
    content = Content,
    created_At = Created_At,
    updated_At = Updated_At
)

fun PlanPlaceResponse.toPlanPlaceModel() = PlanPlaceModel(
    category = Category,
    latitude = Latitude,
    longitude = Longitude,
    name = Name,
    roadAddress = RoadAddress
)

fun PlanFlightResponse.toPlanFlightModel() = PlanFlightModel(
    airlineCode = AirlineCode,
    flightNum = FlightNum,
    departureTime = DepartureTime,
    arrivalTime = ArrivalTime,
    departureAirport = DepartureAirport,
    arrivalAirport = ArrivalAirport
)
