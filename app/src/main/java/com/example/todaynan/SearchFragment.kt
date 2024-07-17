package com.example.todaynan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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

        //임시 데이터
        var rList = ArrayList<Recommend>()
        rList.apply {
            add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀1", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
            add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀2", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
            add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀3", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        }

        val recommendRVAdapter = RecommendRVAdapter(rList)
        binding.resultListRv.adapter = recommendRVAdapter
        binding.resultListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

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