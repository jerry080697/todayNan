package com.example.todaynan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.ActivityBoardReplyBinding


class BoardReplyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardReplyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardReplyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boardReplyBackBtn.setOnClickListener {
            finish()
        }


    }
}
