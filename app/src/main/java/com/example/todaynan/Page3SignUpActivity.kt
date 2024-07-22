package com.example.todaynan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.SignupPage3Binding

class Page3SignUpActivity : AppCompatActivity() {

    lateinit var binding: SignupPage3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupPage3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupPage3Back.setOnClickListener {
            finish()
        }

    }


}