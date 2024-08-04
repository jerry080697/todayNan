package com.example.todaynan.ui.main.board

import com.example.todaynan.databinding.FragmentPostBinding
import com.example.todaynan.ui.BaseFragment

class PostFragment : BaseFragment<FragmentPostBinding>(FragmentPostBinding::inflate){
    override fun initAfterBinding() {
        binding.postBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}