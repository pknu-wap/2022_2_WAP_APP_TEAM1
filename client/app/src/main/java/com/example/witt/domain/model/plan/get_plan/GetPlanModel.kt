package com.example.witt.domain.model.plan.get_plan

data class GetPlanModel (
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
    val userId : String,
    val nickName : String,
    val profileImage : String
)

data class DetailPlanModel (
    val day: Int,
    val memo: PlanMemoModel?,
    val orderIndex: Int,
    val place: PlanPlaceModel?,
    val planId: Int,
    val type: Int
    )

data class PlanMemoModel (
    val content : String,
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