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
import com.example.todaynan.ui.main.search.SearchFragment
import com.example.todaynan.databinding.ActivityMainBinding
import com.example.todaynan.ui.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initAfterBinding() {
        // 회원가입 정보 확인
        Log.d("TAG", AppData.address)
        Log.d("TAG", AppData.perfer.toString())
        Log.d("TAG", AppData.nickname)
        Log.d("TAG", AppData.mypet)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        initBottomNavigation()
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