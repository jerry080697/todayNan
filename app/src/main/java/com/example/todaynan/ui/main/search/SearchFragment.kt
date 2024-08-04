package com.example.todaynan.ui.main.search

import android.view.KeyEvent
import com.example.todaynan.R
import com.example.todaynan.databinding.FragmentSearchBinding
import com.example.todaynan.ui.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override fun initAfterBinding() {

        changeScreen()
    }

    override fun onResume() {
        super.onResume()

        binding.requestEt.text = null
    }

    private fun changeScreen(){
        binding.searchImageBt0.setOnClickListener {
            hideKeyboard()
            val searchText = binding.requestEt.text.toString()
            val resultFragment = ResultFragment.newInstance(searchText)
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, resultFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.requestEt.setOnEditorActionListener { v, actionId, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard()
                val searchText = binding.requestEt.text.toString()
                val resultFragment = ResultFragment.newInstance(searchText)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, resultFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
                true // 이벤트 처리 완료
            } else {
                false // 이벤트 처리 안 함
            }
        }
        binding.searchHomeBt.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ResultFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

}