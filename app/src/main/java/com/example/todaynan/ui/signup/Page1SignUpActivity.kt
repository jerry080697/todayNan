package com.example.todaynan.ui.signup

import android.content.Intent
import android.graphics.Color
import android.view.View
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.SignupData
import com.example.todaynan.databinding.SignupPage1Binding
import com.example.todaynan.ui.BaseActivity

class Page1SignUpActivity : BaseActivity<SignupPage1Binding>(SignupPage1Binding::inflate) {

    override fun initAfterBinding() {
        //val completeAddress = SignupData.completeAddress
        //val selectedOptions = SignupData.selectedOptions

        binding.signupAddressSelectCv.setOnClickListener {
            val intent = Intent(this, Page3SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.signupPlaySelectCv.setOnClickListener {
            val intent = Intent(this, Page8SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()

        if (AppData.address.isNotEmpty()) {
            binding.signupAddressTv.setTextColor(Color.BLACK)
            binding.signupAddressTv.text = AppData.address
        }
        if(AppData.perfer.isNotEmpty()) {
            binding.signupPlaySelectTv.text = AppData.perfer.joinToString(", ")
            binding.signupPlaySelectTv.setTextColor(Color.BLACK)
        }
        if (AppData.address.isNotEmpty() && AppData.perfer.isNotEmpty()) {
            binding.signupNextBtn.visibility = View.INVISIBLE
            binding.signupNextBtnDark.visibility = View.VISIBLE
        }

        if (binding.signupNextBtnDark.visibility == View.VISIBLE) {
            binding.signupNextBtnDark.setOnClickListener {
                val intent = Intent(this, Page2SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
