package com.example.todaynan.ui.main.board

import android.os.Bundle
import com.example.todaynan.databinding.FragmentPostBinding
import com.example.todaynan.ui.BaseFragment

class PostFragment : BaseFragment<FragmentPostBinding>(FragmentPostBinding::inflate){

    companion object {
        fun newInstance(text: String): PostFragment {
            val fragment = PostFragment()
            val args = Bundle()
            args.putString("type", text)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initAfterBinding() {
        val type = arguments?.getString("type")
        binding.postTypeTv.text = type

        binding.postBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}