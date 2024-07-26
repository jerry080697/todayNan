package com.example.todaynan.ui.signup

import com.example.todaynan.databinding.ActivitySignupBinding
import com.example.todaynan.ui.BaseActivity

class SignUpActivity : BaseActivity<ActivitySignupBinding>(ActivitySignupBinding::inflate) {

    override fun initAfterBinding() {

        binding.kakaoBtn.setOnClickListener {
            startNextActivity(Page1SignUpActivity::class.java)
        }

    }
}