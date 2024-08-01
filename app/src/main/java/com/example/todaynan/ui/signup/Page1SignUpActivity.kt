package com.example.todaynan.ui.signup

import android.content.Intent
import android.graphics.Color
import android.view.View
import com.example.todaynan.data.entity.SignupData
import com.example.todaynan.databinding.SignupPage1Binding
import com.example.todaynan.ui.BaseActivity

class Page1SignUpActivity : BaseActivity<SignupPage1Binding>(SignupPage1Binding::inflate) {

    override fun initAfterBinding() {
        val completeAddress = SignupData.completeAddress
        val selectedOptions = SignupData.selectedOptions

        if (completeAddress != null) {
            binding.signupAddressTv.setTextColor(Color.BLACK)
            binding.signupAddressTv.text = completeAddress
        }

        binding.signupAddressSelectCv.setOnClickListener {
            val intent = Intent(this, Page3SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.signupPlaySelectCv.setOnClickListener {
            val intent = Intent(this, Page8SignUpActivity::class.java)
            startActivity(intent)
        }

        if (intent.hasExtra("status")) {
            binding.signupNextBtn.visibility = View.INVISIBLE
            binding.signupNextBtnDark.visibility = View.VISIBLE
        }

        if (binding.signupNextBtnDark.visibility == View.VISIBLE) {
            binding.signupNextBtnDark.setOnClickListener {
                val intent = Intent(this, Page2SignUpActivity::class.java)
                startActivity(intent)
            }
        }

        if (selectedOptions.isNotEmpty()) {
            binding.signupPlaySelectTv.text = selectedOptions.joinToString(", ")
            binding.signupPlaySelectTv.setTextColor(Color.BLACK)
        }
    }
}
