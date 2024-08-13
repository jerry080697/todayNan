package com.example.todaynan.ui.main.search

import android.os.Bundle
import android.view.KeyEvent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.R
import com.example.todaynan.data.remote.place.GeminiItem
import com.example.todaynan.databinding.FragmentResultBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.RecommendRVAdapter

class ResultFragment : BaseFragment<FragmentResultBinding>(FragmentResultBinding::inflate) {

    var showType: Int = 0   //0: 크게 보기, 1: 작게 보기
    private var insideItemList : ArrayList<GeminiItem>? = null

    companion object {
        fun newInstance(text: String): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putString("search_word", text)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initAfterBinding() {

        val word = arguments?.getString("search_word")
        binding.resultEt.setText(word)

        chooseType()
        changeScreen()

        // 안 결과 데이터
        arguments?.let {
            insideItemList = it.getParcelableArrayList("insideItem")
        }

        val recommendRVAdapter1 = RecommendRVAdapter(insideItemList,1)
        binding.resultListRv.adapter = recommendRVAdapter1
        binding.resultListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val recommendRVAdapter2 = RecommendRVAdapter(insideItemList,2)
        binding.resultBlockRv.adapter = recommendRVAdapter2
        binding.resultBlockRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    private fun changeScreen(){
        binding.searchImageBt1.setOnClickListener {
            hideKeyboard()
            binding.resultEt.text = binding.resultEt.text
            // 검색 결과 갱신
        }
        binding.resultEt.setOnEditorActionListener { v, actionId, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard()
                binding.resultEt.text = binding.resultEt.text
                // 검색 결과 갱신
                true // 이벤트 처리 완료
            } else {
                false // 이벤트 처리 안 함
            }
        }

        binding.searchBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun chooseType(){
        binding.resultMenuIv.setOnClickListener{
            if(showType == 0)
                binding.resultMenuIv.setImageResource(R.drawable.search_menu_list_dark)
            else
                binding.resultMenuIv.setImageResource(R.drawable.search_menu_block_dark)

            val typeList = mutableListOf<PopupValue>().apply {
                add(PopupValue(R.drawable.search_menu_list,"크게 보기"))
                add(PopupValue(R.drawable.search_menu_block, "작게 보기"))
            }

            SearchMenuPopup(context = requireContext(), popupList = typeList){ _, _, position->
                when (position) {
                    0 -> { //크게 보기
                        binding.resultMenuIv.setImageResource(R.drawable.search_menu_list)
                        binding.resultListRv.isVisible = true
                        binding.resultBlockRv.isVisible = false
                        showType = 0
                    }

                    1 -> { //작게 보기
                        binding.resultMenuIv.setImageResource(R.drawable.search_menu_block)
                        binding.resultListRv.isVisible = false
                        binding.resultBlockRv.isVisible = true
                        showType = 1
                    }
                }
            }.apply {
                isOutsideTouchable = true
                isTouchable = true
                showAsDropDown(it, -270, 20) //resultMenuIv 기준으로 팝업메뉴 위치
            }
        }
    }

}