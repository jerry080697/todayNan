package com.example.todaynan.ui.main.board

import EmployRegisterFragment
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.post.GetPost
import com.example.todaynan.data.remote.post.LikePost
import com.example.todaynan.data.remote.post.PostInterface
import com.example.todaynan.data.remote.post.PostList
import com.example.todaynan.data.remote.post.PostResponse
import com.example.todaynan.databinding.FragmentDetailBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.PostRVAdapter
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment: BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val postService = getRetrofit().create(PostInterface::class.java)
    private val request = "Bearer "+AppData.appToken
    private lateinit var type: String

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

        type = arguments?.getString("type").toString()
        binding.detailTv.text = type

        if(type == "구인 게시판"){
            employPost()
        } else if(type == "잡담 게시판"){
            chatPost()
        }

        val middleAddress = AppData.address.split(" ").getOrNull(1) ?: "없음"
        binding.locInfoTv.text = middleAddress

        binding.detailRegisterCl.setOnClickListener {
            val employRegisterFragment = if(type == "구인 게시판") {
                EmployRegisterFragment.newInstance("구인 게시판")
            } else{ //type == "잡담 게시판"
                EmployRegisterFragment.newInstance("잡담 게시판")
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, employRegisterFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.detailBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // 페이징 처리
        binding.detailBoardRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(!recyclerView.canScrollVertically(1)){ // 스크롤이 끝에 도달했을 때
                loadNextPage() // 다음 페이지 게시물 가져오기
                }
            }
        })

    }

    fun employPost(){
        postService.getPostEmploy(request,1).enqueue(object :
            Callback<PostResponse<GetPost>> {
            override fun onResponse(
                call: Call<PostResponse<GetPost>>,
                response: Response<PostResponse<GetPost>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())

                val items = resp?.result?.postList?: emptyList()

                if (resp!!.isSuccess) {
                    val boardAdapter = PostRVAdapter(items)
                    binding.detailBoardRv.adapter = boardAdapter
                    binding.detailBoardRv.layoutManager = LinearLayoutManager(context)
                    boardAdapter.setMyItemClickListener(object : PostRVAdapter.MyItemClickListener {
                        override fun onItemClick(post: PostList) {
                            val type = arguments?.getString("type")
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.main_frm, PostFragment().apply{
                                    arguments = Bundle().apply{
                                        val gson = Gson()
                                        val postJson = gson.toJson(post)
                                        val postNicknameJson = post.userNickname
                                        val postIdJson = post.postId
                                        putString("type", type)
                                        var postTypeJson = ""
                                        if(type == "구인 게시판") {
                                            postTypeJson = "구인 게시판"
                                        } else{
                                            postTypeJson = "잡담 게시판"
                                        }
                                        putString("post", postJson)
                                        putString("postType", postTypeJson)
                                        putString("postNickname", postNicknameJson)
                                        putInt("postId", postIdJson)
                                    }
                                })
                                .addToBackStack(null)
                                .commitAllowingStateLoss()
                        }
                    })
                }
            }

            override fun onFailure(call: Call<PostResponse<GetPost>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }
        })
    }

    fun chatPost(){
        postService.getChatEmploy(request,1).enqueue(object :
            Callback<PostResponse<GetPost>> {
            override fun onResponse(
                call: Call<PostResponse<GetPost>>,
                response: Response<PostResponse<GetPost>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())

                val items = resp?.result?.postList?: emptyList()

                if (resp!!.isSuccess) {
                    val boardAdapter = PostRVAdapter(items)
                    binding.detailBoardRv.adapter = boardAdapter
                    binding.detailBoardRv.layoutManager = LinearLayoutManager(context)
                    boardAdapter.setMyItemClickListener(object : PostRVAdapter.MyItemClickListener {
                        override fun onItemClick(post: PostList) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.main_frm, PostFragment().apply{
                                    arguments = Bundle().apply{
                                        val gson = Gson()
                                        val postJson = gson.toJson(post)
                                        val postIdJson = post.postId
                                        putString("type", type)
                                        putString("post", postJson)
                                        putInt("postId", postIdJson)
                                    }
                                })
                                .addToBackStack(null)
                                .commitAllowingStateLoss()
                        }
                    })
                }
            }

            override fun onFailure(call: Call<PostResponse<GetPost>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }
        })
    }

    fun loadNextPage() {

    }



//    private fun generateDummyItems(): List<Post> {
//        val items = ArrayList<Post>()
//        items.add(Post("띠드버거", R.drawable.default_profile_img,"05.06 14:30","마포구 상암동","잠실 진저베어 신상","안녕하떼여 띠드버거임니당!\n이번 주말에 딩딩이랑 잠실에 갔는데요,,,","추천 게시판",21,15,""))
//        items.add(Post("재밌으면 짖는 개", R.drawable.default_profile_img,"05.04 14:20","광명시 철산동","성심당","없어지면 내가 가게 차린다ㅋㅋ\n마라탕후루집으로 차릴거임","잡담 게시판",33,50,""))
//        items.add(Post("AI입니다", R.drawable.default_profile_img,"05.02 11:30","구로구 구로동","현대미술관 띱","저랑 같이 보러 가실 분?\n밥 사드림","구인 게시판",15,10,""))
//        items.add(Post("띠드버거", R.drawable.default_profile_img,"05.06 14:30","마포구 상암동","잠실 진저베어 신상","안녕하떼여 띠드버거임니당!\n이번 주말에 딩딩이랑 잠실에 갔는데요,,,","추천 게시판",21,15,""))
//        items.add(Post("재밌으면 짖는 개", R.drawable.default_profile_img,"05.04 14:20","광명시 철산동","성심당","없어지면 내가 가게 차린다ㅋㅋ\n마라탕후루집으로 차릴거임","잡담 게시판",33,50,""))
//        items.add(Post("AI입니다", R.drawable.default_profile_img,"05.02 11:30","구로구 구로동","현대미술관 띱","저랑 같이 보러 가실 분?\n밥 사드림","구인 게시판",15,10,""))
//        items.add(Post("띠드버거", R.drawable.default_profile_img,"05.06 14:30","마포구 상암동","잠실 진저베어 신상","안녕하떼여 띠드버거임니당!\n이번 주말에 딩딩이랑 잠실에 갔는데요,,,","추천 게시판",21,15,""))
//        items.add(Post("재밌으면 짖는 개", R.drawable.default_profile_img,"05.04 14:20","광명시 철산동","성심당","없어지면 내가 가게 차린다ㅋㅋ\n마라탕후루집으로 차릴거임","잡담 게시판",33,50,""))
//        items.add(Post("AI입니다", R.drawable.default_profile_img,"05.02 11:30","구로구 구로동","현대미술관 띱","저랑 같이 보러 가실 분?\n밥 사드림","구인 게시판",15,10,""))
//        return items
//    }
}