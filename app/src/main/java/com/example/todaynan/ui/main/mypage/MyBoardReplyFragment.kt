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
import com.example.todaynan.databinding.FragmentMyBoardReplyBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.MyBoardReplyRVAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBoardReplyFragment : BaseFragment<FragmentMyBoardReplyBinding>(FragmentMyBoardReplyBinding::inflate) {
    private val userService = getRetrofit().create(PostInterface::class.java)

    private lateinit var myBoardReplyRVAdapter: MyBoardReplyRVAdapter

    override fun initAfterBinding() {

        binding.boardReplyBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        loadingMyWritePost(1)
    }

    private fun loadingMyWritePost(page: Int) {
        val accessToken = "Bearer ${AppData.appToken}"
        userService.loadMyReplyPost(accessToken, page).enqueue(object : Callback<PostResponse<GetPost>> {
            override fun onResponse(call: Call<PostResponse<GetPost>>, response: Response<PostResponse<GetPost>>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    Log.d("MyBoardReplyFragment", "Response Code: ${response.code()}")
                    Log.d("MyBoardReplyFragment", "Response Body: $userResponse")
                    val postResponse = response.body()

                    postResponse?.let {
                        val posts = it.result.postList
                        val topPosts = posts.take(5)
                        setUpRecyclerView(topPosts)
                    }
                } else {
                    Log.d("MyBoardReplyFragment", "Response error: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "서버 응답 오류", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PostResponse<GetPost>>, t: Throwable) {
                Log.d("MyBoardReplyFragment", "Request failed: ${t.message}")
                Toast.makeText(context, "서버와 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setUpRecyclerView(postList: List<PostList>) {
        myBoardReplyRVAdapter = MyBoardReplyRVAdapter(postList)
        binding.boardReplyRv.adapter = myBoardReplyRVAdapter
        binding.boardReplyRv.layoutManager = LinearLayoutManager(context)
    }
}
