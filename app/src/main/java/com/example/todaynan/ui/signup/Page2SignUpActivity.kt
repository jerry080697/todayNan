package com.example.todaynan.ui.signup

import android.content.Intent
import android.view.View
import com.example.todaynan.databinding.SignupPage2Binding
import com.example.todaynan.ui.BaseActivity
import com.example.todaynan.ui.main.MainActivity

class Page2SignUpActivity : BaseActivity<SignupPage2Binding>(SignupPage2Binding::inflate) {

    override fun initAfterBinding() {
        option1()
        option2()
        option3()

        binding.signupLetsgoBtnDark.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    fun selectOption1(isSelect: Boolean) {
        if (isSelect) {
            binding.signupPet1DarkCv.visibility = View.VISIBLE
            binding.signupPet1Cv.visibility = View.INVISIBLE
        } else {
            binding.signupPet1DarkCv.visibility = View.INVISIBLE
            binding.signupPet1Cv.visibility = View.VISIBLE
        }
    }

    fun selectOption2(isSelect: Boolean) {
        if (isSelect) {
            binding.signupPet2DarkCv.visibility = View.VISIBLE
            binding.signupPet2Cv.visibility = View.INVISIBLE
        } else {
            binding.signupPet2DarkCv.visibility = View.INVISIBLE
            binding.signupPet2Cv.visibility = View.VISIBLE
        }
    }

    fun selectOption3(isSelect: Boolean) {
        if (isSelect) {
            binding.signupPet3DarkCv.visibility = View.VISIBLE
            binding.signupPet3Cv.visibility = View.INVISIBLE
        } else {
            binding.signupPet3DarkCv.visibility = View.INVISIBLE
            binding.signupPet3Cv.visibility = View.VISIBLE
        }
    }

    fun option1() {
        binding.signupPet1Cv.setOnClickListener {
            selectOption1(true)
            checkToNext()
            duplicateCheck(1)
        }
        binding.signupPet1DarkCv.setOnClickListener {
            selectOption1(false)
            checkToNext()
            duplicateCheck(-1)
        }
    }

    fun option2() {
        binding.signupPet2Cv.setOnClickListener {
            selectOption2(true)
            checkToNext()
            duplicateCheck(2)
        }
        binding.signupPet2DarkCv.setOnClickListener {
            selectOption2(false)
            checkToNext()
            duplicateCheck(-1)
        }
    }

    fun option3() {
        binding.signupPet3Cv.setOnClickListener {
            selectOption3(true)
            checkToNext()
            duplicateCheck(3)
        }
        binding.signupPet3DarkCv.setOnClickListener {
            selectOption3(false)
            checkToNext()
            duplicateCheck(-1)
        }
    }

    private fun checkToNext() {
        if (binding.signupPet1DarkCv.visibility == View.VISIBLE || binding.signupPet2DarkCv.visibility == View.VISIBLE
            || binding.signupPet3DarkCv.visibility == View.VISIBLE) {
            binding.signupLetsgoBtn.visibility = View.INVISIBLE
            binding.signupLetsgoBtnDark.visibility = View.VISIBLE
        } else {
            binding.signupLetsgoBtn.visibility = View.VISIBLE
            binding.signupLetsgoBtnDark.visibility = View.INVISIBLE
        }
    }

    private fun duplicateCheck(selectedOption: Int) {
        when (selectedOption) {
            1 -> {
                binding.signupPet2DarkCv.visibility = View.INVISIBLE
                binding.signupPet2Cv.visibility = View.VISIBLE
                binding.signupPet3DarkCv.visibility = View.INVISIBLE
                binding.signupPet3Cv.visibility = View.VISIBLE
            }
            2 -> {
                binding.signupPet1DarkCv.visibility = View.INVISIBLE
                binding.signupPet1Cv.visibility = View.VISIBLE
                binding.signupPet3DarkCv.visibility = View.INVISIBLE
                binding.signupPet3Cv.visibility = View.VISIBLE
            }
            3 -> {
                binding.signupPet1DarkCv.visibility = View.INVISIBLE
                binding.signupPet1Cv.visibility = View.VISIBLE
                binding.signupPet2DarkCv.visibility = View.INVISIBLE
                binding.signupPet2Cv.visibility = View.VISIBLE
            }
            else -> {
                // 모든 옵션을 활성화 상태로 되돌림
                binding.signupPet1Cv.visibility = View.VISIBLE
                binding.signupPet1DarkCv.visibility = View.INVISIBLE
                binding.signupPet2Cv.visibility = View.VISIBLE
                binding.signupPet2DarkCv.visibility = View.INVISIBLE
                binding.signupPet3Cv.visibility = View.VISIBLE
                binding.signupPet3DarkCv.visibility = View.INVISIBLE
            }
        }
    }
}
