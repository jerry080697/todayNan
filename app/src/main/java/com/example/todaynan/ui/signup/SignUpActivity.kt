package com.example.todaynan.ui.signup

import android.content.Intent
import android.util.Log
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.GoogleRequest
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.user.GoogleResponse
import com.example.todaynan.data.remote.user.Login
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.databinding.ActivitySignupBinding
import com.example.todaynan.ui.BaseActivity
import com.example.todaynan.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.gson.GsonBuilder
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
                    // accessToken 발급
                    if (account.serverAuthCode != null){
                        Log.d("TAG", "authCode Try!")
                        getAccessToken(account.serverAuthCode!!)
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                    Log.d("TAG", e.toString())
                }
            }
        }
    }
    private fun getAccessToken(authCode:String) {
        val gson = GsonBuilder().setLenient().create()
        val gServer = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserInterface::class.java)

        gServer.getAccessToken(
            request = GoogleRequest(
                grant_type = "authorization_code",
                client_id = getString(R.string.google_client_id),
                client_secret = getString(R.string.google_client_secret),
                code = authCode
            )
        ).enqueue(object : Callback<GoogleResponse> {
            override fun onResponse(call: Call<GoogleResponse>, response: Response<GoogleResponse>) {
                if(response.isSuccessful) {
                    val accessToken = response.body()?.access_token.orEmpty()

                    Log.d("TAG", "SUCCESS_accessToken: $accessToken")
                    AppData.socialToken = accessToken
                    available()
                }
            }
            override fun onFailure(call: Call<GoogleResponse>, t: Throwable) {
                Log.e("TAG", "FAIL_accessToken: ",t.fillInStackTrace() )
            }
        })
    }

    // 기존 계정이 있는지 로그인 시도
    private fun available(){
        val userService = getRetrofit().create(UserInterface::class.java)

        userService.login(AppData.socialToken, AppData.socialType).enqueue(object :
            Callback<UserResponse<Login>>{
            override fun onResponse(
                call: Call<UserResponse<Login>>,
                response: Response<UserResponse<Login>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())

                val sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("socialToken", AppData.socialToken)
                editor.putString("socialType", AppData.socialType)
                if(resp!!.isSuccess){
                    editor.putString("appToken", resp!!.result.accessToken)
                    editor.apply()

                    startNextActivity(MainActivity::class.java)
                }else{
                    startNextActivity(Page1SignUpActivity::class.java)
                }

            }

            override fun onFailure(call: Call<UserResponse<Login>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }

        })
    }
}