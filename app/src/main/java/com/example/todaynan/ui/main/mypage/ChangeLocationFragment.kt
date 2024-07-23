package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentChangeLocationBinding

class ChangeLocationFragment : Fragment() {

    private lateinit var binding: FragmentChangeLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeLocationBinding.inflate(inflater, container, false)


        binding.changeLocationBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return binding.root
    }
}
