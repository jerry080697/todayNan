package com.example.todaynan.ui.main.mypage

import android.view.View
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.databinding.FragmentMyPageBinding
import com.example.todaynan.ui.BaseFragment

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::inflate) {

    override fun initAfterBinding() {
        if(AppData.mypet=="DOG"){
            binding.mypagePetFox.visibility = View.VISIBLE
            binding.mypagePetBear.visibility = View.GONE
            binding.mypagePetBird.visibility = View.GONE
        }
        else if(AppData.mypet=="QUOKKA"){
            binding.mypagePetFox.visibility = View.GONE
            binding.mypagePetBear.visibility = View.GONE
            binding.mypagePetBird.visibility = View.VISIBLE
        }
        else if(AppData.mypet=="CAT"){
            binding.mypagePetFox.visibility = View.GONE
            binding.mypagePetBear.visibility = View.VISIBLE
            binding.mypagePetBird.visibility = View.GONE
        }

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