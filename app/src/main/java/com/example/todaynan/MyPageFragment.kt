package com.example.todaynan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentMyPageBinding
import android.content.*

class MyPageFragment : Fragment() {

    lateinit var binding: FragmentMyPageBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)


        binding.mypageChangeInfoIv.setOnClickListener {
            val intent=Intent(requireContext(),ChangeInfoActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}