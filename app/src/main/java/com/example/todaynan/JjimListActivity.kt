package com.example.todaynan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.ActivityJjimListBinding

class JjimListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJjimListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJjimListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.jjimListBackBtn.setOnClickListener {
            finish()
        }
    }
}
