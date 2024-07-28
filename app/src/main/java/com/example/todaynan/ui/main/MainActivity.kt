package com.example.todaynan.ui.main

import com.example.todaynan.ui.main.board.BoardFragment
import com.example.todaynan.ui.main.location.LocationFragment
import com.example.todaynan.ui.main.mypage.MyPageFragment
import com.example.todaynan.R
import com.example.todaynan.ui.main.search.SearchFragment
import com.example.todaynan.databinding.ActivityMainBinding
import com.example.todaynan.ui.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initAfterBinding() {
        initBottomNavigation()
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