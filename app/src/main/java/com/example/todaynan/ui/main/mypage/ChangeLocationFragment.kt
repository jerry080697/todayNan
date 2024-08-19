package com.example.todaynan.ui.main.mypage

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
import android.view.View
import com.example.todaynan.data.entity.User
import com.google.gson.Gson

class ChangeLocationFragment : BaseFragment<FragmentChangeLocationBinding>(FragmentChangeLocationBinding::inflate) {

    private val userService = getRetrofit().create(UserInterface::class.java)

    override fun initAfterBinding() {

        val newAddress = arguments?.getString("new_address")
        if (newAddress != null) {
            binding.changeLocationCurrentLocationTv.text = AppData.address
            binding.changeLocationSelectLocationTv.text = newAddress
            binding.changeLocationSelectLocationTv.setTextColor(resources.getColor(R.color.black, null))

            binding.changeLocationChangeBtnLight.visibility=View.GONE
            binding.changeLocationChangeBtnDark.visibility=View.VISIBLE
        } else {
            binding.changeLocationCurrentLocationTv.text = AppData.address
            binding.changeLocationSelectLocationTv.text = "주소 선택"
        }

        binding.changeLocationBackBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ChangeInfoFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.changeLocationCv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ChangeNewLocationFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.changeLocationChangeBtnDark.setOnClickListener {
            val newLocation = binding.changeLocationSelectLocationTv.text.toString()

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
                    AppData.address = newLocation
                    // 주소를 SharedPreferences에 저장
                    saveAddressToPreferences(newLocation)
                    binding.changeLocationCurrentLocationTv.text = newLocation
                    binding.changeLocationSelectLocationTv.text = "입력"
                    binding.changeLocationSelectLocationTv.setTextColor(resources.getColor(R.color.gray1, null))
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
        val user: User = User(AppData.nickname, AppData.preferIdx, AppData.address, AppData.mypet)
        val sharedPreferences = requireContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val userJson = Gson().toJson(user)
        editor.putString("user", userJson)
        editor.apply() // or editor.commit() to save synchronously
    }
}
