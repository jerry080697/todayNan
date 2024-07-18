package com.example.todaynan

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.SignupPage1Binding

class Page1SignUp : AppCompatActivity() {

    lateinit var binding: SignupPage1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupPage1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupAddressSelectCv.setOnClickListener {
            startActivity(Intent(this,Page3SignUp::class.java))
        }

        binding.signupPlaySelectCv.setOnClickListener {
            startActivity(Intent(this,Page8SignUp::class.java))
        }

        // 관심사 선택 후 다음으로 버튼 이미지 변경
        if(intent.hasExtra("status")){
            binding.signupNextBtn.visibility = View.INVISIBLE
            binding.signupNextBtnDark.visibility = View.VISIBLE
        }

        if(binding.signupNextBtnDark.visibility == View.VISIBLE){
           binding.signupNextBtnDark.setOnClickListener {
               startActivity(Intent(this,Page2SignUp::class.java))
           }
        }


    }

}