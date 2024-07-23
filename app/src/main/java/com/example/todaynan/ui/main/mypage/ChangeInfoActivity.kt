package com.example.todaynan.ui.main.mypage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.ActivityChangeInfoBinding

class ChangeInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeInfoBackBtn.setOnClickListener {
          finish()
        }
        binding.changeNickname.setOnClickListener{
            val intent=Intent(this, ChangeNicknameActivity::class.java)
            startActivity(intent)
        }
        binding.changeLocation.setOnClickListener {
            val intent=Intent(this, ChangeLocationActivity::class.java)
            startActivity(intent)
        }
        binding.changeInterest.setOnClickListener {
            val intent=Intent(this, ChangeInterestActivity::class.java)
            startActivity(intent)
        }
        binding.changeWithdraw.setOnClickListener {
            val intent=Intent(this, ChangeWithdrawActivity::class.java)
            startActivity(intent)
        }
        binding.changeLogout.setOnClickListener {
            val intent=Intent(this, ChangeLogoutActivity::class.java)
            startActivity(intent)
        }
    }
}
