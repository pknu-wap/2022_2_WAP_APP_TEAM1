package com.example.witt.data.model.plan.get_plan

import com.example.witt.domain.model.plan.get_plan.*

data class GetPlanResponse (
    val status: Boolean,
    val reason: String,
    val data : PlanDataResponse,
)

data class PlanDataResponse(
    val EndDate: String,
    val Name: String,
    val OwnerId: String,
    val Participant: List<PlanParticipant>?,
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
    val Type: Int
)

data class PlanParticipant (
    val UserId : String,
    val Nickname : String,
    val ProfileImage : String
    )

data class PlanMemoResponse(
    val Content : String,
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

fun GetPlanResponse.toGetPlanModel() = GetPlanModel(
    status = status,
    reason = reason,
    data = data.toPlanDataModel()
)

fun PlanDataResponse.toPlanDataModel() = PlanDataModel(
    endDate = EndDate,
    name = Name,
    ownerId = OwnerId,
    participant = Participant?.map { it.toPlanParticipantsModel() },
    plans = Plans.map { it.toDetailPlanModel() },
    region = Region,
    startDate = StartDate,
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
    type = Type
)

fun PlanMemoResponse.toPlanMemoModel() = PlanMemoModel(
    content = Content,
    created_At = Created_At,
    updated_At = Updated_At
)

fun PlanPlaceResponse.toPlanPlaceModel() = PlanPlaceModel(
    category = Category ,
    latitude = Latitude ,
    longitude = Longitude ,
    name = Name ,
    roadAddress = RoadAddress
)