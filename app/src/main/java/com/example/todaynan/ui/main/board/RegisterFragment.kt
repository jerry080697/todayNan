package com.example.todaynan.ui.main.board

import android.view.KeyEvent
import com.example.todaynan.databinding.FragmentRegisterBinding
import com.example.todaynan.ui.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    override fun initAfterBinding() {

        binding.registerBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.registerTitleEt.setOnEditorActionListener { v, actionId, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard()
                true
            } else {
                false
            }
        }

    }
}