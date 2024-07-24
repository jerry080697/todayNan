package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentBoardBinding
import com.example.todaynan.databinding.FragmentChangeNicknameBinding
import com.example.todaynan.ui.BaseFragment

class ChangeNicknameFragment : BaseFragment<FragmentChangeNicknameBinding>(FragmentChangeNicknameBinding::inflate) {

    override fun initAfterBinding() {

        binding.changeNicknameBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
