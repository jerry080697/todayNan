package com.example.todaynan.base

import android.app.Application
import com.example.todaynan.R
import com.kakao.sdk.common.KakaoSdk

class KaKaoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, getString(R.string.kakao_native_key))
    }
}