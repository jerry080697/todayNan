package com.example.todaynan.ui.main.search

import android.view.KeyEvent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.R
import com.example.todaynan.ui.adapter.RecommendRVAdapter
import com.example.todaynan.data.entity.Recommend
import com.example.todaynan.databinding.FragmentSearchBinding
import com.example.todaynan.ui.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    var showType: Int = 0   //0: 나열형, 1: 블록형

    override fun initAfterBinding() {

        search()
        chooseType()

        //임시 데이터
        var rList = ArrayList<Recommend>()
        rList.apply {
            add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀1", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
            add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀2", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
            add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀3", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        }

        val recommendRVAdapter1 = RecommendRVAdapter(rList,1)
        binding.resultListRv.adapter = recommendRVAdapter1
        binding.resultListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val recommendRVAdapter2 = RecommendRVAdapter(rList,2)
        binding.resultBlockRv.adapter = recommendRVAdapter2
        binding.resultBlockRv.layoutManager = GridLayoutManager(context, 2)
    }

    private fun search(){
        binding.searchHomeBt.setOnClickListener {
            binding.searchRequest.isVisible = false
            binding.searchResult.isVisible = true
            //상단바
            binding.searchHomeIcIv.isVisible = true
            binding.searchBar1.isVisible = false
        }

        // 검색 초기화면에서 검색 요청
        binding.searchImageBt0.setOnClickListener {
            hideKeyboard()
            result()
            binding.resultEt.text = binding.requestEt.text
        }
        binding.requestEt.setOnEditorActionListener { v, actionId, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard()
                result()
                binding.resultEt.text = binding.requestEt.text
                true // 이벤트 처리 완료
            } else {
                false // 이벤트 처리 안 함
            }
        }
        // 검색 결과화면에서 검색 요청
        binding.searchImageBt1.setOnClickListener {
            hideKeyboard()
            result()
            binding.resultEt.text = binding.resultEt.text
        }
        binding.resultEt.setOnEditorActionListener { v, actionId, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard()
                binding.resultEt.text = binding.resultEt.text
                result()
                true // 이벤트 처리 완료
            } else {
                false // 이벤트 처리 안 함
            }
        }

        binding.searchBackIv.setOnClickListener {
            binding.requestEt.text = null
            binding.searchRequest.isVisible = true
            binding.searchResult.isVisible = false
        }
    }
    private fun result() {
        // 화면 변경
        binding.searchRequest.isVisible = false
        binding.searchResult.isVisible = true
        // 상단바
        binding.searchHomeIcIv.isVisible = false
        binding.searchBar1.isVisible = true
    }

    private fun chooseType(){
        binding.resultMenuIv.setOnClickListener{
            if(showType == 0)
                binding.resultMenuIv.setImageResource(R.drawable.search_menu_list_dark)
            else
                binding.resultMenuIv.setImageResource(R.drawable.search_menu_block_dark)

            val typeList = mutableListOf<PopupValue>().apply {
                add(PopupValue(R.drawable.search_menu_list,"나열형"))
                add(PopupValue(R.drawable.search_menu_block, "블록형"))
            }

            SearchMenuPopup(context = requireContext(), popupList = typeList){ _, _, position->
                when (position) {
                    0 -> { //나열형
                        binding.resultMenuIv.setImageResource(R.drawable.search_menu_list)
                        binding.resultListRv.isVisible = true
                        binding.resultBlockRv.isVisible = false
                        showType = 0
                    }

                    1 -> { //블록형
                        binding.resultMenuIv.setImageResource(R.drawable.search_menu_block)
                        binding.resultListRv.isVisible = false
                        binding.resultBlockRv.isVisible = true
                        showType = 1
                    }
                }
            }.apply {
                isOutsideTouchable = true
                isTouchable = true
                showAsDropDown(it, -250, 20) //resultMenuIv 기준으로 팝업메뉴 위치
            }
        }
    }

}