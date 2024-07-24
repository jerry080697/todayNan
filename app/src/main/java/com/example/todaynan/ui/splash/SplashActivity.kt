package com.example.todaynan.ui.splash

import android.os.Handler
import android.os.Looper
import com.example.todaynan.R
import com.example.todaynan.databinding.ActivitySplashBinding
import com.example.todaynan.ui.BaseActivity
import com.example.todaynan.ui.signup.SignUpActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var currentScreen = 0

    override fun initAfterBinding() {
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                when (currentScreen) {
                    0 -> setContentView(R.layout.splash_screen1)
                    1 -> setContentView(R.layout.splash_screen2)
                    2 -> setContentView(R.layout.splash_screen3)
                    else -> {
                        startActivityWithClear(SignUpActivity::class.java)
                        finish()
                    }
                }
                currentScreen++
                handler.postDelayed(this, 1250) // 1.25초마다 화면 전환
            }
        }
        handler.postDelayed(runnable, 1250) // 1.25초 후에 첫 실행
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}


