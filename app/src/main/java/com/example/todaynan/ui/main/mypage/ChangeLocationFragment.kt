package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentBoardBinding
import com.example.todaynan.databinding.FragmentChangeLocationBinding
import com.example.todaynan.ui.BaseFragment

class ChangeLocationFragment : BaseFragment<FragmentChangeLocationBinding>(FragmentChangeLocationBinding::inflate) {

    override fun initAfterBinding() {

        binding.changeLocationBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }
}
