package com.example.todaynan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        search()

        return binding.root
    }

    private fun search(){
        binding.searchImageBt0.setOnClickListener {
            binding.searchRequest.isVisible = false
            binding.searchResult.isVisible = true
            binding.resultEt.text = binding.requestEt.text
        }
        binding.searchBackIv.setOnClickListener {
            binding.requestEt.text = null
            binding.searchRequest.isVisible = true
            binding.searchResult.isVisible = false
        }
    }

}