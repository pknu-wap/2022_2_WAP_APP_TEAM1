package com.example.witt.utils

import com.kakao.sdk.template.model.*

object DefaultTemplate {
    val kakaoTemplate by lazy{
        FeedTemplate(
            content = Content(
                title = "WitT의 여행에 초대합니다!",
                description = "일정을 함께 작성해보세요.",
                imageUrl = "https://avatars.githubusercontent.com/u/77484719?v=4",
                link = Link(
                    webUrl = "https://developers.kakao.com",
                    mobileWebUrl = "https://developers.kakao.com"
                )
            ),
            buttons = listOf(
                Button(
                    "초대 수락하기",
                    Link(
                        androidExecutionParams = mapOf("key1" to "value1")
                    )
                )
            )
        )
    }
}