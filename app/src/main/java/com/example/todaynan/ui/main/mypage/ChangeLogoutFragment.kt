package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentChangeLogoutBinding

class ChangeLogoutFragment : Fragment() {

    private lateinit var binding: FragmentChangeLogoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeLogoutBinding.inflate(inflater, container, false)


        binding.changeLogoutBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return binding.root
    }
}
