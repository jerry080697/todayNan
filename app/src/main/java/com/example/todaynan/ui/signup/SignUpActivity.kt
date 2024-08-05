package com.example.todaynan.ui.signup

import android.util.Log
import com.example.todaynan.databinding.ActivitySignupBinding
import com.example.todaynan.ui.BaseActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class SignUpActivity : BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate) {

    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("TAG", "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i("TAG", "카카오계정으로 로그인 성공 ${token.accessToken}")
            startNextActivity(Page1SignUpActivity::class.java)
        }
    }

    override fun initAfterBinding() {

        binding.kakaoBtn.setOnClickListener {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoCallback)
        }

    }
}