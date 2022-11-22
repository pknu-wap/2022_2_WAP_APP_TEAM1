package com.example.witt.data.repository

import com.example.witt.data.mapper.toMakePlanResponseModel
import com.example.witt.data.model.plan.get_plan.toGetPlanListModel
import com.example.witt.data.model.plan.get_plan.toGetPlanModel
import com.example.witt.data.model.plan.make_plan.request.MakePlanRequest
import com.example.witt.data.model.plan.memo.EditMemoRequest
import com.example.witt.data.model.plan.memo.toEditMemoModel
import com.example.witt.data.model.plan.memo.toMakeMemoModel
import com.example.witt.data.source.remote.memo.EditMemoDataSource
import com.example.witt.data.source.remote.memo.MakeMemoDataSource
import com.example.witt.data.source.remote.plan.get_plan.GetPlanDataSource
import com.example.witt.data.source.remote.plan.get_plan.GetPlanListDataSource
import com.example.witt.data.source.remote.plan.make_plan.MakePlanDataSource
import com.example.witt.domain.model.plan.get_plan.GetPlanListModel
import com.example.witt.domain.model.plan.get_plan.GetPlanModel
import com.example.witt.domain.model.plan.make_plan.MakePlanModel
import com.example.witt.domain.model.plan.make_plan.MakePlanResponseModel
import com.example.witt.domain.model.plan.memo.EditMemoModel
import com.example.witt.domain.model.plan.memo.MakeMemoModel
import com.example.witt.domain.repository.PlanRepository
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(
    private val makePlanDataSource: MakePlanDataSource,
    private val getPlanListDataSource: GetPlanListDataSource,
    private val getPlanDataSource: GetPlanDataSource,
    private val makeMemoDataSource: MakeMemoDataSource,
    private val editMemoDataSource: EditMemoDataSource
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

    override suspend fun makeMemo(tripId : Int, day: Int, content: String): Result<MakeMemoModel> {
        return makeMemoDataSource.makeMemo(tripId, day, content).mapCatching { response ->
            response.toMakeMemoModel()
        }
    }

    override suspend fun editMemo(tripId: Int, Content: String, planId: Int, ): Result<EditMemoModel> {
        return editMemoDataSource.editMemo(tripId = tripId, request = EditMemoRequest(Content, planId)
            ).mapCatching { response ->
            response.toEditMemoModel()
        }
    }
}
