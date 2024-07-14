package com.example.todaynan

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var currentScreen = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen1)

        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                currentScreen++
                when (currentScreen) {
                    1 -> setContentView(R.layout.splash_screen2)
                    2 -> setContentView(R.layout.splash_screen3)
                    else -> {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }
                }
                handler.postDelayed(this, 2000) // 2초마다 화면 전환
            }
        }
        handler.postDelayed(runnable, 2000) // 2초 후에 첫 실행
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}


