package com.example.witt

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.internal.Contexts.getApplication

@HiltAndroidApp
class WittApplication :Application(){
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this,com.example.witt.BuildConfig.KAKAO_NATIVE_APP_KEY)
        NaverIdLoginSDK.initialize(this, com.example.witt.BuildConfig.NAVER_CLIENT_ID, com.example.witt.BuildConfig.NAVER_CLIENT_SECRET, "witt")
    }
}