package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todaynan.R
import com.example.todaynan.databinding.FragmentBoardBinding
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
                .replace(R.id.main_frm, PostFragment())
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