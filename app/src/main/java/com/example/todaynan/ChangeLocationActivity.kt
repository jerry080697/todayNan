package com.example.todaynan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynan.databinding.ActivityChagneLocationBinding

class ChangeLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChagneLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChagneLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeLocationBackBtn.setOnClickListener {
            finish()
        }
    }
}
