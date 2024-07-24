package com.example.todaynan.ui.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.SignupPage1Binding
import com.example.todaynan.databinding.SignupPage3Binding
import com.example.todaynan.ui.BaseActivity

class Page3SignUpActivity : BaseActivity<SignupPage3Binding>(SignupPage3Binding::inflate) {
    override fun initAfterBinding() {

        binding.signupPage3Back.setOnClickListener {
            finish()
        }

    }


}