package com.example.todaynan.ui.main.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {

    lateinit var binding: FragmentBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardBinding.inflate(inflater, container, false)

        return binding.root
    }
}