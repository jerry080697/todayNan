package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.widget.Toast
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.ChangeLocationRequest
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.ChangeLocationResponse
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.databinding.FragmentChangeLocationBinding
import com.example.todaynan.ui.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Context

class ChangeLocationFragment : BaseFragment<FragmentChangeLocationBinding>(FragmentChangeLocationBinding::inflate) {

    private val userService = getRetrofit().create(UserInterface::class.java)

    override fun initAfterBinding() {

        // SharedPreferences에서 저장된 주소를 불러와 AppData.address에 설정
        val newAddress = arguments?.getString("new_address")
        if (newAddress != null) {
            binding.changeLocationCurrentLocationTv.text = newAddress
            binding.changeLocationSelectLocationTv.text = newAddress
            AppData.address = newAddress
        }
        val currentAddress = loadAddressFromPreferences().takeIf { it.isNotEmpty() } ?: AppData.address
        binding.changeLocationCurrentLocationTv.text = currentAddress

        binding.changeLocationBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.changeLocationCv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ChangeNewLocationFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.changeLocationChangeBtnIv.setOnClickListener {
            val newLocation = AppData.address
            if (newLocation.isNotEmpty()) {
                sendChangeLocationRequest(newLocation)
            } else {
                Toast.makeText(context, "새로운 동네를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendChangeLocationRequest(newLocation: String) {
        val request = ChangeLocationRequest(address = newLocation)
        val accessToken = "Bearer ${AppData.appToken}"

        userService.changeLocation(accessToken, request).enqueue(object : Callback<UserResponse<ChangeLocationResponse>> {
            override fun onResponse(
                call: Call<UserResponse<ChangeLocationResponse>>,
                response: Response<UserResponse<ChangeLocationResponse>>
            ) {
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    // 주소를 SharedPreferences에 저장
                    saveAddressToPreferences(newLocation)

                    AppData.address = newLocation // AppData의 address 업데이트
                    binding.changeLocationCurrentLocationTv.text = newLocation
                    binding.changeLocationSelectLocationTv.text = newLocation
                    Toast.makeText(context, "동네가 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "동네 변경에 실패했습니다: ${response.body()?.message ?: "Unknown error"}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse<ChangeLocationResponse>>, t: Throwable) {
                Toast.makeText(context, "서버와 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveAddressToPreferences(address: String) {
        val sharedPreferences = requireContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("address", address)
        editor.apply() // or editor.commit() to save synchronously
    }

    private fun loadAddressFromPreferences(): String {
        val sharedPreferences = requireContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        return sharedPreferences.getString("address", "") ?: ""
    }
}
