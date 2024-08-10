package com.example.todaynan.ui.main

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import com.example.todaynan.ui.main.board.BoardFragment
import com.example.todaynan.ui.main.location.LocationFragment
import com.example.todaynan.ui.main.mypage.MyPageFragment
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.user.Login
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.ui.main.search.SearchFragment
import com.example.todaynan.databinding.ActivityMainBinding
import com.example.todaynan.ui.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val userService = getRetrofit().create(UserInterface::class.java)

    override fun initAfterBinding() {
        // 회원가입 정보 확인
        val sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
        AppData.appToken = sharedPreferences.getString("appToken", "").toString()
        AppData.socialType = sharedPreferences.getString("socialType", "").toString()
        AppData.socialToken = sharedPreferences.getString("socialToken", "").toString()
        Log.d("TAG_tokenInMain", AppData.appToken)
        Log.d("TAG_socialTokenInMain", AppData.socialToken)
        Log.d("TAG_socialType", AppData.socialType)
        Log.d("TAG", AppData.address)
        Log.d("TAG", AppData.preferIdx.toString())
        Log.d("TAG", AppData.nickname)
        Log.d("TAG", AppData.mypet)

        autoLogin()

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        initBottomNavigation()
    }

    private fun autoLogin(){
        userService.autoLogin().enqueue(object :
            Callback<UserResponse<Login>> {
            override fun onResponse(
                call: Call<UserResponse<Login>>,
                response: Response<UserResponse<Login>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())
                if(resp != null){
                    if(!resp.isSuccess){    // false = 토큰 만료
                        login()     // 재로그인하여 accessToken 재발급
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse<Login>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }
        })
    }

    private fun login(){
        userService.login(AppData.socialToken, AppData.socialType).enqueue(object :
            Callback<UserResponse<Login>>{
            override fun onResponse(
                call: Call<UserResponse<Login>>,
                response: Response<UserResponse<Login>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())

                if(resp!!.isSuccess) {
                    val sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    // 토큰
                    editor.putString("appToken", resp!!.result.accessToken)
                    AppData.appToken = resp!!.result.accessToken
                    Log.d("TAG_tokenReissue", AppData.appToken)
                    editor.apply()
                }
            }

            override fun onFailure(call: Call<UserResponse<Login>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }
        })
    }

    // 뒤로가기 버튼 눌렀을 때 발동되는 함수 (두번 눌러야 종료)
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        var isExit : Boolean = false
        override fun handleOnBackPressed() {
            // 백 스택의 크기를 확인
            val backStackCount = supportFragmentManager.backStackEntryCount

            if(backStackCount == 0){
                if(isExit) {
                    ActivityCompat.finishAffinity(this@MainActivity)
                    System.runFinalization()
                    System.exit(0)
                }
                else {
                    Toast.makeText(this@MainActivity, "종료하려면 뒤로가기를 한 번 더 누르세요.", Toast.LENGTH_SHORT).show()
                }

                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    isExit = true
                }, 300)
                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    isExit = false
                }, 3000)
            }else {
                // 백 스택의 크기가 1이 아니면 백 스택에서 이전 프래그먼트로 이동
                supportFragmentManager.popBackStack()
            }
        }
    }

    private fun initBottomNavigation(){
        binding.mainBnv.menu.findItem(R.id.searchFragment).setIcon(R.drawable.ic_search_on)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, SearchFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            // 모든 아이템의 아이콘을 기본 아이콘으로 재설정
            binding.mainBnv.menu.findItem(R.id.searchFragment).setIcon(R.drawable.ic_search_off)
            binding.mainBnv.menu.findItem(R.id.locationFragment).setIcon(R.drawable.ic_location_off)
            binding.mainBnv.menu.findItem(R.id.boardFragment).setIcon(R.drawable.ic_board_off)
            binding.mainBnv.menu.findItem(R.id.myPageFragment).setIcon(R.drawable.ic_mypage_off)

            // 선택된 항목의 아이콘 변경 및 Fragment 전환
            when (item.itemId) {

                R.id.searchFragment -> {
                    item.setIcon(R.drawable.ic_search_on)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.locationFragment -> {
                    item.setIcon(R.drawable.ic_location_on)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LocationFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.boardFragment -> {
                    item.setIcon(R.drawable.ic_board_on)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, BoardFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.myPageFragment -> {
                    item.setIcon(R.drawable.ic_mypage_on)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MyPageFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

}