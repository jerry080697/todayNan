package com.example.todaynan.ui.main.board

import EmployRegisterFragment
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.Post
import com.example.todaynan.data.entity.PostWrite
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.user.PostResponse
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.databinding.FragmentDetailBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.PostRVAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    // Retrofit 인터페이스 초기화
    private val userService = getRetrofit().create(UserInterface::class.java)

    companion object {
        fun newInstance(text: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString("type", text)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initAfterBinding() {
        val type = arguments?.getString("type")
        binding.detailTv.text = type

        // 서버에서 게시글 가져오기
        fetchPostsFromServer()

        // 검색 버튼 클릭 시 서버 요청
        binding.searchImageBt1.setOnClickListener {
            hideKeyboard()
            // 검색 결과 서버 요청 (추가 구현 필요)
        }

        // 엔터키 입력 시 서버 요청
        binding.resultEt.setOnEditorActionListener { v, actionId, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard()
                // 검색 결과 서버 요청 (추가 구현 필요)
                true // 이벤트 처리 완료
            } else {
                false // 이벤트 처리 안 함
            }
        }

        // 게시글 작성 화면으로 이동
        binding.detailRegisterCl.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, EmployRegisterFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        // 뒤로가기 버튼 클릭 시
        binding.detailBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun fetchPostsFromServer() {
        // 예시 데이터로 PostWrite 객체 생성
        val postWrite = PostWrite(content = "Sample Content" ,title = "Sample Title" ,category = "General")
        val request = "Bearer "+ AppData.appToken

        // 서버에 게시글 요청
        userService.post(request, postWrite).enqueue(object : Callback<UserResponse<PostResponse>> {
            override fun onResponse(
                call: Call<UserResponse<PostResponse>>,
                response: Response<UserResponse<PostResponse>>
            ) {
                if (response.isSuccessful) {
                    // 서버로부터 받은 데이터 처리
                    val postResponse = response.body()?.result
                    if (postResponse != null) {
                        val post = Post(
                            userName = "Sample User",
                            profileImg = R.drawable.default_profile_img,
                            creationTime = "2024-08-11 12:34",
                            location = "Sample Location",
                            postTitle = postResponse.title,
                            postContent = postResponse.content,
                            boardTitle = "General",
                            likedNum = 0,
                            replyNum = 0,
                            category = postResponse.category
                        )

                        // RecyclerView 업데이트
                        updateRecyclerView(listOf(post))
                    }
                } else {
                    Log.e("DetailFragment", "Server Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<UserResponse<PostResponse>>, t: Throwable) {
                Log.e("DetailFragment", "Network Error: ${t.message}")
            }
        })
    }

    private fun updateRecyclerView(posts: List<Post>) {
        val postAdapter = PostRVAdapter(posts)
        binding.detailBoardRv.adapter = postAdapter

        // 아이템 클릭 이벤트 처리
        postAdapter.setMyItemClickListner(object : PostRVAdapter.MyItemClickListner {
            override fun onItemClick(post: Post) {
                val postFragment = PostFragment.newInstance(post.postTitle)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, postFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        })
    }
}
