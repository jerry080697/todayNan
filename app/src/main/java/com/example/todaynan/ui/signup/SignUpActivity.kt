package com.example.todaynan.ui.signup

import android.content.Intent
import android.util.Log
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.databinding.ActivitySignupBinding
import com.example.todaynan.ui.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class SignUpActivity : BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate) {

    companion object {
        private const val GOOGLE_REQUEST = 1000
    }
    // 구글 서버 접속 시 필요 변수
    private val googleOpt: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(
                Scope("https://www.googleapis.com/auth/userinfo.email")
            )
            .requestServerAuthCode(getString(R.string.google_client_id))
            .requestEmail()
            .build()
    }
    private val googleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(this, googleOpt)
    }
    // 카카오 서버 접속 시 필요 변수
    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("TAG", "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i("TAG", "카카오계정으로 로그인 성공 ${token.accessToken}")
            startNextActivity(Page1SignUpActivity::class.java)
        }
    }

    override fun initAfterBinding() {

        binding.googleBtn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_REQUEST)
            AppData.socialType = "GOOGLE"
        }
        binding.kakaoBtn.setOnClickListener {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoCallback)
            AppData.socialType = "KAKAO"
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("TAG_r", requestCode.toString())
        when(requestCode) {
            GOOGLE_REQUEST -> {
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val account = task.result!!

                    Log.d("TAG", "Email: ${account.email ?: ""}")
                    Log.d("TAG", "authCode: ${account.serverAuthCode}")
                } catch (e: ApiException) {
                    e.printStackTrace()
                    Log.d("TAG", e.toString())
                }
            }
        }
    }
}