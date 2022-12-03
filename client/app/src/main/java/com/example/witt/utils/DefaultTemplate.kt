package com.example.witt.utils

import com.example.witt.domain.model.use_case.plan.PlanStateModel
import com.kakao.sdk.template.model.*

object DefaultTemplate {

    fun createTemplate(plan: PlanStateModel): FeedTemplate{
        return FeedTemplate(
            content = Content(
                title = "'${plan.Name}' 여행에 초대합니다!",
                description = "일정을 함께 작성해보세요.",
                imageUrl = "https://github.com/pknu-wap/2022_2_WAP_A.PP_TEAM1/blob/develop_android/image/icon.png?raw=true",
                link = Link(
                    webUrl = "https://developers.kakao.com",
                    mobileWebUrl = "https://developers.kakao.com"
                )
            ),
            buttons = listOf(
                Button(
                    "초대 수락하기",
                    Link(
                        androidExecutionParams = mapOf(
                            "tripId" to plan.TripId.toString(),
                            "tripName" to plan.Name,
                            "tripDate" to plan.StartDate + '~' + plan.EndDate
                        )
                    )
                )
            )
        )
    }
}