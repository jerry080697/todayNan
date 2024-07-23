package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentChangeWithdrawBinding

class ChangeWithdrawFragment : Fragment() {

    private lateinit var binding: FragmentChangeWithdrawBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeWithdrawBinding.inflate(inflater, container, false)


        binding.changeWithdrawBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return binding.root
    }
}
