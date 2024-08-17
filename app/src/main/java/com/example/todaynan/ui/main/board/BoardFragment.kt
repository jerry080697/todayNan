package com.example.todaynan.ui.main.board

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.post.GetPost
import com.example.todaynan.data.remote.post.PostInterface
import com.example.todaynan.data.remote.post.PostList
import com.example.todaynan.data.remote.post.PostResponse
import com.example.todaynan.databinding.FragmentBoardBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.BoardRVAdapter
import com.example.todaynan.ui.main.mypage.HotBoardFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardFragment : BaseFragment<FragmentBoardBinding>(FragmentBoardBinding::inflate) {
    private val userService = getRetrofit().create(PostInterface::class.java)
    private lateinit var boardRVAdapter: BoardRVAdapter
    override fun initAfterBinding() {
        loadingHotBoard(1)
        binding.hotBoard.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HotBoardFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.recruitBoard.setOnClickListener{
            val recruitFragment = DetailFragment.newInstance("구인 게시판")
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, recruitFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.talkBoard.setOnClickListener{
            val recruitFragment = DetailFragment.newInstance("잡담 게시판")
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, recruitFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }
    private fun loadingHotBoard(page: Int) {
        val accessToken = "Bearer ${AppData.appToken}"
        userService.loadHotBoard(accessToken, page).enqueue(object :
            Callback<PostResponse<GetPost>> {
            override fun onResponse(call: Call<PostResponse<GetPost>>, response: Response<PostResponse<GetPost>>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    Log.d("BoardFragment", "Response Code: ${response.code()}")
                    Log.d("BoardFragment", "Response Body: $userResponse")
                    val postResponse = response.body()

                    postResponse?.let {
                        val posts = it.result.postList
                        val topPosts = posts.take(5)
                        setUpRecyclerView(topPosts)
                    }
                } else {
                    Log.d("BoardFragment", "Response error: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "서버 응답 오류", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PostResponse<GetPost>>, t: Throwable) {
                Log.d("BoardFragment", "Request failed: ${t.message}")
                Toast.makeText(context, "서버와 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setUpRecyclerView(postList: List<PostList>) {
        boardRVAdapter = BoardRVAdapter(postList)
        binding.hotPostRv.adapter = boardRVAdapter
        binding.hotPostRv.layoutManager = LinearLayoutManager(context)

        boardRVAdapter.setMyItemClickListener(object : BoardRVAdapter.MyItemClickListener {
            override fun onItemClick(post: PostList) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, HotBoardFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        })
    }
}