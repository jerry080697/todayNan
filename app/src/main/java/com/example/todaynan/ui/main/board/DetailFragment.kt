package com.example.todaynan.ui.main.board

import EmployRegisterFragment
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.post.GetPost
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
    private var page = 1
    private var last = false
    private var items: MutableList<PostList> = mutableListOf()

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
            employPost(page)
        } else if(type == "잡담 게시판"){
            chatPost(page)
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
                    if(!last){
                        page++
                        if(type == "구인 게시판"){
                            employPost(page)
                        } else if(type == "잡담 게시판"){
                            chatPost(page)
                        }
                    }else{
                        Toast.makeText(recyclerView.context, "마지막 게시글입니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

    }

    fun employPost(p: Int){
        postService.getPostEmploy(request, p).enqueue(object :
            Callback<PostResponse<GetPost>> {
            override fun onResponse(
                call: Call<PostResponse<GetPost>>,
                response: Response<PostResponse<GetPost>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())

                val boardAdapter = PostRVAdapter(items)
                if (resp!!.isSuccess) {
                    resp?.result?.postList?.let { newList ->
                        val uniqueItems = newList.filterNot { it in items }
                        items.addAll(0, uniqueItems)
                    }
                    last = resp.result.isLast
                    if(page == 1){
                        binding.detailBoardRv.adapter = boardAdapter
                        binding.detailBoardRv.layoutManager = LinearLayoutManager(context)
                    }else{
                        binding.detailBoardRv.adapter!!.notifyDataSetChanged()
                    }
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

    fun chatPost(p: Int){
        postService.getChatEmploy(request,p).enqueue(object :
            Callback<PostResponse<GetPost>> {
            override fun onResponse(
                call: Call<PostResponse<GetPost>>,
                response: Response<PostResponse<GetPost>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())

                val boardAdapter = PostRVAdapter(items)
                if (resp!!.isSuccess) {
                    resp?.result?.postList?.let { newList ->
                        val uniqueItems = newList.filterNot { it in items }
                        items.addAll(0, uniqueItems)
                    }
                    last = resp.result.isLast
                    if(page == 1){
                        binding.detailBoardRv.adapter = boardAdapter
                        binding.detailBoardRv.layoutManager = LinearLayoutManager(context)
                    }else{
                        binding.detailBoardRv.adapter!!.notifyDataSetChanged()
                    }
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

}