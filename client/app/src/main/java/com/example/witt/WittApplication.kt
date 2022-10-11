package com.example.witt

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WittApplication :Application(){
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this,com.example.witt.BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}