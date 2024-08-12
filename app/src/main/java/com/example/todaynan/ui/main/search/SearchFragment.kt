package com.example.todaynan.ui.main.search

import android.view.KeyEvent
import android.view.View
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.databinding.FragmentSearchBinding
import com.example.todaynan.ui.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override fun initAfterBinding() {

        if(AppData.mypet == "DOG"){
            binding.searchPetFox.visibility = View.VISIBLE
            binding.searchPetBird.visibility = View.GONE
            binding.searchPetBear.visibility = View.GONE
        }else if(AppData.mypet == "CAT"){
            binding.searchPetBird.visibility = View.VISIBLE
            binding.searchPetFox.visibility = View.GONE
            binding.searchPetBear.visibility = View.GONE
        }else if(AppData.mypet == "QUOKKA"){
            binding.searchPetBear.visibility = View.VISIBLE
            binding.searchPetFox.visibility = View.GONE
            binding.searchPetBird.visibility = View.GONE
        }

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