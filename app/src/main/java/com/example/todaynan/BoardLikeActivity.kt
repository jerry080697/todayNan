package com.example.todaynan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.ActivityBoardLikeBinding


class BoardLikeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardLikeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardLikeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boardLikeBackBtn.setOnClickListener {
            finish()
        }


    }
}
