package com.example.todaynan.ui.main.mypage

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.Recommend
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.user.PlaceLikeLoadResult
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.UserLikeItem
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.databinding.FragmentJjimListBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.JjimListRVAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JjimListFragment : BaseFragment<FragmentJjimListBinding>(FragmentJjimListBinding::inflate) {

    private val userService = getRetrofit().create(UserInterface::class.java)
    override fun initAfterBinding() {
        loadPlaceLikes()
    }

    private fun loadPlaceLikes() {
        val accessToken = "Bearer "+ AppData.appToken

        userService.placeLikeLoad(accessToken).enqueue(object : Callback<UserResponse<PlaceLikeLoadResult>> {
            override fun onResponse(
                call: Call<UserResponse<PlaceLikeLoadResult>>,
                response: Response<UserResponse<PlaceLikeLoadResult>>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()?.result?.userLikeItems ?: emptyList()
                    setupRecyclerView(result)
                } else {

                }
            }

            override fun onFailure(call: Call<UserResponse<PlaceLikeLoadResult>>, t: Throwable) {
                Toast.makeText(context, "서버 응답 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(items: List<UserLikeItem>) {
        val adapter = JjimListRVAdapter(items)
        binding.jjimListRv.layoutManager = LinearLayoutManager(context)
        binding.jjimListRv.adapter = adapter
    }
}
