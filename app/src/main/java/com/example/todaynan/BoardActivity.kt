package com.example.todaynan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.ActivityBoardBinding
import com.example.todaynan.databinding.ActivityJjimListBinding

class BoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.boardBackBtn.setOnClickListener {
            finish()
        }
        binding.likeBoard.setOnClickListener{
            val intent=Intent(this,BoardLikeActivity::class.java)
            startActivity(intent)
        }
        binding.replyBoard.setOnClickListener{
            val intent=Intent(this,BoardReplyActivity::class.java)
            startActivity(intent)
        }
        binding.writeBoard.setOnClickListener{
            val intent=Intent(this,BoardWriteActivity::class.java)
            startActivity(intent)
        }




    }
}
