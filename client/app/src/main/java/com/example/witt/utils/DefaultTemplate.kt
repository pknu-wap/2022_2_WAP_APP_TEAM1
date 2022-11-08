package com.example.witt.utils

import com.kakao.sdk.template.model.*

object KakaoLinkTemplate {
    val defaultTemplate by lazy{
        FeedTemplate(
            content = Content(
                title = "WitT의 여행에 초대합니다!",
                description = "witT_feed",
                imageUrl = "https://avatars.githubusercontent.com/u/77484719?v=4",
                link = Link(
                    webUrl = "https://developers.kakao.com",
                    mobileWebUrl = "https://developers.kakao.com"
                )
            ),
            social = Social(
                likeCount = 286,
                commentCount = 45,
                sharedCount = 845
            ),
            buttons = listOf(
                Button(
                    "앱으로 보기",
                    Link(
                        androidExecutionParams = mapOf("key1" to "value1", "key2" to "value2")
                    )
                )
            )
        )
    }
}