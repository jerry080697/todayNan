package com.example.todaynan.ui.main.mypage

import com.example.todaynan.R
import com.example.todaynan.databinding.FragmentChangeInfoBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.signup.ChangeInterestFragment

class ChangeInfoFragment : BaseFragment<FragmentChangeInfoBinding>(FragmentChangeInfoBinding::inflate) {

    override fun initAfterBinding() {

        binding.changeInfoBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.changeNickname.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ChangeNicknameFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.changeLocation.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ChangeLocationFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.changeInterest.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ChangeInterestFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.changeWithdraw.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ChangeSignOutFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

    }
}
