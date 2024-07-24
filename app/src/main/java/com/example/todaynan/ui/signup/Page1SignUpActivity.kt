package com.example.todaynan.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.SignupPage1Binding
import com.example.todaynan.ui.BaseActivity

class Page1SignUpActivity : BaseActivity<SignupPage1Binding>(SignupPage1Binding::inflate) {

    override fun initAfterBinding() {

        binding.signupAddressSelectCv.setOnClickListener {
            startNextActivity(Page3SignUpActivity::class.java)
        }

        binding.signupPlaySelectCv.setOnClickListener {
            startNextActivity(Page8SignUpActivity::class.java)
        }


        if(intent.hasExtra("status")){
            binding.signupNextBtn.visibility = View.INVISIBLE
            binding.signupNextBtnDark.visibility = View.VISIBLE
        }

        //다음으로 이동
        if(binding.signupNextBtnDark.visibility == View.VISIBLE){
           binding.signupNextBtnDark.setOnClickListener {
               startNextActivity(Page2SignUpActivity::class.java)
           }
        }

        if(intent.hasExtra("option2")){
            binding.signupPlaySelectTv.text = intent.getStringExtra("option2")
        }

    }

}