package com.example.todaynan.ui.main.mypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.ChangeNewNicknameRequest
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.user.NicknameDuplicateResponse
import com.example.todaynan.data.remote.user.ChangeNickNameResponse
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.databinding.FragmentChangeNicknameBinding
import com.example.todaynan.ui.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeNicknameFragment : BaseFragment<FragmentChangeNicknameBinding>(FragmentChangeNicknameBinding::inflate) {

    private val userService = getRetrofit().create(UserInterface::class.java)
    private var isNicknameChecked = false

    override fun initAfterBinding() {
        // SharedPreferences에서 현재 닉네임 로드, 없으면 AppData.nickname 사용
        val currentNickname = loadNicknameFromPreferences().takeIf { it.isNotEmpty() } ?: AppData.nickname
        binding.changeNicknameCurrentNicknameTv.text = currentNickname

        binding.changeNicknameBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.changeNicknameCheckDuplicateIv.setOnClickListener {
            val newNickname = binding.changeNicknameNewNicknameEt.text.toString()
            if (newNickname.isNotEmpty()) {
                checkNicknameDuplicate(newNickname)
            } else {
                Toast.makeText(context, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.changeNicknameChangeBtnIv.setOnClickListener {
            val newNickname = binding.changeNicknameNewNicknameEt.text.toString()
            if (newNickname.isNotEmpty()) {
                if (binding.changeNicknameAlertMessagePass.visibility == View.VISIBLE) {
                    sendNicknameChangeRequest(newNickname)
                }
            } else {
                Toast.makeText(context, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkNicknameDuplicate(nickname: String) {
        val accessToken = "Bearer "+AppData.appToken
        userService.checkNicknameDuplicate(accessToken,nickname).enqueue(object : Callback<UserResponse<NicknameDuplicateResponse>> {
            override fun onResponse(call: Call<UserResponse<NicknameDuplicateResponse>>, response: Response<UserResponse<NicknameDuplicateResponse>>) {
                Log.d("ChangeNicknameFragment", "Nickname check response code: ${response.code()}")
                Log.d("ChangeNicknameFragment", "Nickname check response body: ${response.body()}")

                if (response.body()?.isSuccess == true) {
                    isNicknameChecked = true
                    binding.changeNicknameAlertMessagePass.visibility = View.VISIBLE
                    binding.changeNicknameAlertMessageFail.visibility = View.GONE
                } else {
                    isNicknameChecked = false
                    binding.changeNicknameAlertMessagePass.visibility = View.GONE
                    binding.changeNicknameAlertMessageFail.visibility = View.VISIBLE
                }
            }
            override fun onFailure(call: Call<UserResponse<NicknameDuplicateResponse>>, t: Throwable) {
                isNicknameChecked = false
                Log.e("ChangeNicknameFragment", "Network error during nickname check: ${t.message}", t)
                Toast.makeText(context, "서버와 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendNicknameChangeRequest(newNickname: String) {
        val request = ChangeNewNicknameRequest(nickname = newNickname)
        val accessToken = "Bearer "+AppData.appToken

        userService.changeNickname(accessToken, request).enqueue(object : Callback<UserResponse<ChangeNickNameResponse>> {
            override fun onResponse(call: Call<UserResponse<ChangeNickNameResponse>>, response: Response<UserResponse<ChangeNickNameResponse>>) {
                Log.d("ChangeNicknameFragment", "Response code: ${response.code()}")
                Log.d("ChangeNicknameFragment", "Response body: ${response.body()}")
                Log.d("ChangeNicknameFragment", "Response message: ${response.message()}")

                if (response.isSuccessful) {
                    saveNicknameToPreferences(newNickname)
                    binding.changeNicknameCurrentNicknameTv.text = newNickname
                    Toast.makeText(context, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("ChangeNicknameFragment", "Error response: $errorMsg")
                    Toast.makeText(context, "닉네임 변경에 실패했습니다: ${response.body()?.message ?: errorMsg}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<UserResponse<ChangeNickNameResponse>>, t: Throwable) {
                Log.e("ChangeNicknameFragment", "Network error: ${t.message}", t)
                Toast.makeText(context, "서버와 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun saveNicknameToPreferences(nickname: String) {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("nickname", nickname)
        editor.apply()
    }
    private fun loadNicknameFromPreferences(): String {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("nickname", "") ?: ""
    }
}
