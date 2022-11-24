package com.example.witt.data.repository

import com.example.witt.data.mapper.toMakePlanResponseModel
import com.example.witt.data.model.plan.get_plan.toGetPlanListModel
import com.example.witt.data.model.plan.get_plan.toGetPlanModel
import com.example.witt.data.model.plan.join_plan.toJoinPlanModel
import com.example.witt.data.model.plan.join_plan.toOutPlanModel
import com.example.witt.data.model.plan.make_plan.request.MakePlanRequest
import com.example.witt.data.model.plan.remove_plan.toRemovePlanModel
import com.example.witt.data.source.remote.plan.get_plan.GetPlanDataSource
import com.example.witt.data.source.remote.plan.get_plan.GetPlanListDataSource
import com.example.witt.data.source.remote.plan.make_plan.MakePlanDataSource
import com.example.witt.data.source.remote.plan.join_plan.JoinPlanDataSource
import com.example.witt.data.source.remote.plan.join_plan.OutPlanDataSource
import com.example.witt.data.source.remote.plan.remove_plan.RemovePlanDataSource
import com.example.witt.domain.model.plan.get_plan.GetPlanListModel
import com.example.witt.domain.model.plan.get_plan.GetPlanModel
import com.example.witt.domain.model.plan.make_plan.MakePlanModel
import com.example.witt.domain.model.plan.make_plan.MakePlanResponseModel
import com.example.witt.domain.model.plan.join_plan.JoinPlanModel
import com.example.witt.domain.model.plan.join_plan.OutPlanModel
import com.example.witt.domain.model.plan.remove_plan.RemovePlanModel
import com.example.witt.domain.repository.PlanRepository
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(
    private val makePlanDataSource: MakePlanDataSource,
    private val getPlanListDataSource: GetPlanListDataSource,
    private val getPlanDataSource: GetPlanDataSource,
    private val removePlanDataSource: RemovePlanDataSource,
    private val joinPlanDataSource: JoinPlanDataSource,
    private val outPlanDataSource: OutPlanDataSource
): PlanRepository{
    override suspend fun makePlan(makePlanModel: MakePlanModel): Result<MakePlanResponseModel> {
        return makePlanDataSource.makePlan(
            MakePlanRequest(
                StartDate = makePlanModel.StartDate,
                EndDate = makePlanModel.EndDate,
                Name = makePlanModel.Name,
                Region = makePlanModel.Region
            )
        ).mapCatching { response ->
            response.toMakePlanResponseModel()
        }
    }

    override suspend fun getPlanList(): Result<GetPlanListModel> {
        return getPlanListDataSource.getPlanList().mapCatching{ response ->
            response.toGetPlanListModel()
        }
    }

    override suspend fun getPlan(planId: Int): Result<GetPlanModel> {
        return getPlanDataSource.getPlan(planId).mapCatching { response ->
            response.toGetPlanModel()
        }
    }

    override suspend fun removePlan(tripId: Int): Result<RemovePlanModel> {
        return removePlanDataSource.removePlan(tripId).mapCatching { response ->
            response.toRemovePlanModel()
        }
    }

    override suspend fun joinPlan(tripId: Int): Result<JoinPlanModel> {
        return joinPlanDataSource.joinPlan(tripId).mapCatching { response ->
            response.toJoinPlanModel()
        }
    }

    override suspend fun outPlan(tripId: Int): Result<OutPlanModel> {
        return outPlanDataSource.outPlan(tripId).mapCatching { response ->
            response.toOutPlanModel()
        }
    }
}
