package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.ActivityChangeLogoutBinding

class ChangeLogoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeLogoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeLogoutBackBtn.setOnClickListener {
            finish()
        }
    }
}
