package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.ChangeNewNicknameRequest
import com.example.todaynan.data.remote.getRetrofit
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

    override fun initAfterBinding() {

        binding.changeNicknameBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.changeNicknameChangeBtnIv.setOnClickListener {
            val newNickname = binding.changeNicknameNewNicknameEt.text.toString()
            if (newNickname.isNotEmpty()) {
                sendNicknameChangeRequest(newNickname)
            } else {
                Toast.makeText(context, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.changeNicknameCurrentNicknameTv.text = AppData.nickname
    }

    private fun sendNicknameChangeRequest(newNickname: String) {
        val request = ChangeNewNicknameRequest(nickname = newNickname)

        val accessToken = AppData.appToken

        userService.changeNickname(accessToken, request).enqueue(object : Callback<UserResponse<ChangeNickNameResponse>> {
            override fun onResponse(call: Call<UserResponse<ChangeNickNameResponse>>, response: Response<UserResponse<ChangeNickNameResponse>>) {
                Log.d("ChangeNicknameFragment", "Response code: ${response.code()}")
                Log.d("ChangeNicknameFragment", "Response body: ${response.body()}")
                Log.d("ChangeNicknameFragment", "Response message: ${response.message()}")

                if (response.body()?.isSuccess == true) {
                    AppData.nickname = newNickname
                    binding.changeNicknameCurrentNicknameTv.text = newNickname
                    Toast.makeText(context, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "닉네임 변경에 실패했습니다: ${response.body()?.message ?: "Unknown error"}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse<ChangeNickNameResponse>>, t: Throwable) {
                Toast.makeText(context, "서버와 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
