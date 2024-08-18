package com.example.todaynan.ui.main.mypage

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.base.AppData
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.post.PostResponse
import com.example.todaynan.data.remote.post.GetPost
import com.example.todaynan.data.remote.post.PostInterface
import com.example.todaynan.data.remote.post.PostList
import com.example.todaynan.databinding.FragmentHotBoardBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.BoardRVAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotBoardFragment : BaseFragment<FragmentHotBoardBinding>(FragmentHotBoardBinding::inflate) {
    private val userService = getRetrofit().create(PostInterface::class.java)

    private lateinit var hotBoardRVAdapter: BoardRVAdapter

    override fun initAfterBinding() {

        binding.hotBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        loadingHotBoard(1)
    }

    private fun loadingHotBoard(page: Int) {
        val accessToken = "Bearer ${AppData.appToken}"
        userService.loadHotBoard(accessToken, page).enqueue(object : Callback<PostResponse<GetPost>> {
            override fun onResponse(call: Call<PostResponse<GetPost>>, response: Response<PostResponse<GetPost>>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    Log.d("HotBoardFragment", "Response Code: ${response.code()}")
                    Log.d("HotBoardFragment", "Response Body: $userResponse")
                    val postResponse = response.body()

                    postResponse?.let {
                        val posts = it.result.postList
                        val topPosts = posts.take(5)
                        setUpRecyclerView(topPosts)
                    }
                } else {
                    Log.d("HotBoardFragment", "Response error: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "서버 응답 오류", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PostResponse<GetPost>>, t: Throwable) {
                Log.d("HotBoardFragment", "Request failed: ${t.message}")
                Toast.makeText(context, "서버와 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setUpRecyclerView(postList: List<PostList>) {
        hotBoardRVAdapter = BoardRVAdapter(postList)
        binding.hotBoardRv.adapter = hotBoardRVAdapter
        binding.hotBoardRv.layoutManager = LinearLayoutManager(context)
    }
}
