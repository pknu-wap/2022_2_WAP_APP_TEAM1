package com.example.witt.domain.use_case.remote

import com.example.witt.domain.model.remote.plan.make_plan.MakePlanModel
import com.example.witt.domain.model.remote.plan.make_plan.MakePlanResponseModel
import com.example.witt.domain.repository.PlanRepository
import javax.inject.Inject

class MakePlanUseCase @Inject constructor(
    private val repository: PlanRepository
) {
    suspend operator fun invoke(makePlanModel: MakePlanModel): Result<MakePlanResponseModel> =
        repository.makePlan(makePlanModel)
}
