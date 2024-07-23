package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentChangeInterestBinding

class ChangeInterestFragment : Fragment() {

    private lateinit var binding: FragmentChangeInterestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeInterestBinding.inflate(inflater, container, false)


        binding.changeInterestBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return binding.root
    }
}
