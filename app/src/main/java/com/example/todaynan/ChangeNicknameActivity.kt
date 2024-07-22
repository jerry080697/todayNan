package com.example.todaynan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.ActivityChangeNicknameBinding

class ChangeNicknameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeNicknameBackBtn.setOnClickListener {
            finish()
        }
    }
}
