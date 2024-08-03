package com.example.todaynan.ui.main.mypage

import com.example.todaynan.R
import com.example.todaynan.databinding.FragmentMyPageBinding
import com.example.todaynan.ui.BaseFragment

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::inflate) {

    override fun initAfterBinding() {

        binding.mypageChangeInfoIv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ChangeInfoFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.mypageListIv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, JjimListFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.mypagePostIv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ChatFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.mypageBoardIv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MyBoardFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

    }
}