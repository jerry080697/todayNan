package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentBoardBinding
import com.example.todaynan.databinding.FragmentChangeInterestBinding
import com.example.todaynan.ui.BaseFragment

class ChangeInterestFragment : BaseFragment<FragmentChangeInterestBinding>(FragmentChangeInterestBinding::inflate) {

    override fun initAfterBinding() {

        binding.changeInterestBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }
}
