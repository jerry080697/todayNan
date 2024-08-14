package com.example.todaynan.ui.signup

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.User
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.user.NicknameDuplicateResponse
import com.example.todaynan.data.remote.user.Token
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.databinding.SignupPage2Binding
import com.example.todaynan.ui.BaseActivity
import com.example.todaynan.ui.main.MainActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Page2SignUpActivity : BaseActivity<SignupPage2Binding>(SignupPage2Binding::inflate) {
    private val userService = getRetrofit().create(UserInterface::class.java)

    override fun initAfterBinding() {
        option1()
        option2()
        option3()

        binding.signupPage2Et.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                AppData.nickname = binding.signupPage2Et.text.toString()
                hideKeyboard(binding.signupPage2Et)
                true // 이벤트 처리 완료
            } else {
                false // 이벤트 처리 안 함
            }
        }
        binding.signupLetsgoBtnDark.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            appSignUp()

            finish()
        }
        binding.signupDuplicationCheckIvLight.setOnClickListener {
            val nickname = binding.signupPage2Et.text.toString()
            if (nickname.isNotEmpty()) {
                checkNicknameDuplication(nickname)
            } else {
                Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkNicknameDuplication(nickname: String) {
        val accessToken = "Bearer ${AppData.appToken}"
        userService.checkNicknameDuplicate(accessToken,nickname)
            .enqueue(object : Callback<UserResponse<NicknameDuplicateResponse>> {
                override fun onResponse(
                    call: Call<UserResponse<NicknameDuplicateResponse>>,
                    response: Response<UserResponse<NicknameDuplicateResponse>>
                ) {
                    Log.d("SERVER/SUCCESS", response.toString())
                    val resp = response.body()
                    Log.d("SERVER/SUCCESS", resp.toString())

                    if (response.isSuccessful && resp?.isSuccess == true) {
                        Toast.makeText(
                            this@Page2SignUpActivity,
                            "사용 가능한 닉네임입니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        hideKeyboard(binding.signupDuplicationCheckIvLight)
                    } else {
                        Toast.makeText(
                            this@Page2SignUpActivity,
                            "닉네임이 이미 존재합니다: ${resp?.message ?: "Unknown error"}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<UserResponse<NicknameDuplicateResponse>>,
                    t: Throwable
                ) {
                    Log.d("SERVER/FAILURE", t.message.toString())
                    Toast.makeText(
                        this@Page2SignUpActivity,
                        "서버와 통신 중 오류가 발생했습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun appSignUp() {
        val gson: Gson = Gson()
        val userService = getRetrofit().create(UserInterface::class.java)
        val user = User(AppData.nickname, AppData.preferIdx, AppData.address, AppData.mypet)

        userService.signUp(AppData.socialToken, AppData.socialType, user).enqueue(object :
            Callback<UserResponse<Token>> {
            override fun onResponse(
                call: Call<UserResponse<Token>>,
                response: Response<UserResponse<Token>>
            ) {
                Log.d("SERVER/SUCCESS_signup", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS_signup", resp.toString())

                val sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                // 토큰
                editor.putString("appToken", resp!!.result.accessToken)
                // 회원정보
                val userJson = gson.toJson(user)
                editor.putString("user", userJson)
                editor.apply()
            }

            override fun onFailure(call: Call<UserResponse<Token>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }

        })
    }

    fun selectOption1(isSelect: Boolean) {
        if (isSelect) {
            binding.foxOffIv.visibility = View.INVISIBLE
            binding.foxOnIv.visibility = View.VISIBLE
            binding.foxTextTv.visibility = View.VISIBLE
            AppData.mypet = "DOG"
        } else {
            binding.foxOffIv.visibility = View.VISIBLE
            binding.foxOnIv.visibility = View.INVISIBLE
            binding.foxTextTv.visibility = View.INVISIBLE
            AppData.mypet = ""
        }
    }

    fun selectOption2(isSelect: Boolean) {
        if (isSelect) {
            binding.birdOffIv.visibility = View.INVISIBLE
            binding.birdOnIv.visibility = View.VISIBLE
            binding.birdTextTv.visibility = View.VISIBLE
            AppData.mypet = "CAT"
        } else {
            binding.birdOffIv.visibility = View.VISIBLE
            binding.birdOnIv.visibility = View.INVISIBLE
            binding.birdTextTv.visibility = View.INVISIBLE
            AppData.mypet = ""
        }
    }

    fun selectOption3(isSelect: Boolean) {
        if (isSelect) {
            binding.bearOffIv.visibility = View.INVISIBLE
            binding.bearOnIv.visibility = View.VISIBLE
            binding.bearTextTv.visibility = View.VISIBLE
            AppData.mypet = "QUOKKA"
        } else {
            binding.bearOffIv.visibility = View.VISIBLE
            binding.bearOnIv.visibility = View.INVISIBLE
            binding.bearTextTv.visibility = View.INVISIBLE
            AppData.mypet = ""
        }
    }

    fun option1() {
        binding.foxOffIv.setOnClickListener {
            selectOption1(true)
            checkToNext()
            duplicateCheck(1)
        }
        binding.foxOnIv.setOnClickListener {
            selectOption1(false)
            checkToNext()
            duplicateCheck(-1)
        }
    }

    fun option2() {
        binding.birdOffIv.setOnClickListener {
            selectOption2(true)
            checkToNext()
            duplicateCheck(2)
        }
        binding.birdOnIv.setOnClickListener {
            selectOption2(false)
            checkToNext()
            duplicateCheck(-1)
        }
    }

    fun option3() {
        binding.bearOffIv.setOnClickListener {
            selectOption3(true)
            checkToNext()
            duplicateCheck(3)
        }
        binding.bearOnIv.setOnClickListener {
            selectOption3(false)
            checkToNext()
            duplicateCheck(-1)
        }
    }

    private fun checkToNext() {
        if (binding.foxOnIv.visibility == View.VISIBLE || binding.birdOnIv.visibility == View.VISIBLE
            || binding.bearOnIv.visibility == View.VISIBLE) {
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
                binding.birdOnIv.visibility = View.INVISIBLE
                binding.birdTextTv.visibility = View.INVISIBLE
                binding.birdOffIv.visibility = View.VISIBLE
                binding.bearOnIv.visibility = View.INVISIBLE
                binding.bearTextTv.visibility = View.INVISIBLE
                binding.bearOffIv.visibility = View.VISIBLE
            }
            2 -> {
                binding.foxOnIv.visibility = View.INVISIBLE
                binding.foxTextTv.visibility = View.INVISIBLE
                binding.foxOffIv.visibility = View.VISIBLE
                binding.bearOnIv.visibility = View.INVISIBLE
                binding.bearTextTv.visibility = View.INVISIBLE
                binding.bearOffIv.visibility = View.VISIBLE
            }
            3 -> {
                binding.foxOnIv.visibility = View.INVISIBLE
                binding.foxTextTv.visibility = View.INVISIBLE
                binding.foxOffIv.visibility = View.VISIBLE
                binding.birdOnIv.visibility = View.INVISIBLE
                binding.birdTextTv.visibility = View.INVISIBLE
                binding.birdOffIv.visibility = View.VISIBLE
            }
            else -> {
                // 모든 옵션을 활성화 상태로 되돌림
                binding.foxOffIv.visibility = View.VISIBLE
                binding.foxOnIv.visibility = View.INVISIBLE
                binding.birdOffIv.visibility = View.VISIBLE
                binding.birdOnIv.visibility = View.INVISIBLE
                binding.bearOffIv.visibility = View.VISIBLE
                binding.bearOnIv.visibility = View.INVISIBLE
            }
        }
    }
}
