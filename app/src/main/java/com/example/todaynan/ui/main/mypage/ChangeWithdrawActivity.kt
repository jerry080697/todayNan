package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.ActivityChangeWithdrawBinding

class ChangeWithdrawActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeWithdrawBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeWithdrawBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeWithdrawBackBtn.setOnClickListener {
            finish()
        }
    }
}
