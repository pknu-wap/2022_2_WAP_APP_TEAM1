package com.example.witt.domain.model.remote.plan.get_plan

data class GetPlanModel(
    val status: Boolean,
    val reason: String,
    val data: PlanDataModel,
)

data class PlanDataModel(
    val endDate: String,
    val name: String,
    val ownerId: String,
    val participants: List<PlanParticipantModel>?,
    val plans: List<DetailPlanModel>,
    val region: String,
    val startDate: String,
    val tripId: Int
)

data class PlanParticipantModel(
    val userId: String,
    val nickName: String,
    val profileImage: String
)

data class DetailPlanModel(
    val day: Int,
    val memo: PlanMemoModel?,
    val orderIndex: Int,
    val place: PlanPlaceModel?,
    val planId: Int,
    val type: Int,
    val flight: PlanFlightModel?
)

data class PlanMemoModel(
    val content: String,
    val created_At: String,
    val updated_At: String
)

data class PlanPlaceModel(
    val category: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val roadAddress: String
)

data class PlanFlightModel(
    val airlineCode: String,
    val flightNum: String,
    val departureTime: String,
    val arrivalTime: String,
    val departureAirport: String,
    val arrivalAirport: String
)
