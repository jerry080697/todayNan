package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.ActivityChangeInterestBinding

class ChangeInterestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeInterestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeInterestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeInterestBackBtn.setOnClickListener {
            finish()
        }
    }
}
