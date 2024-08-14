package com.example.todaynan.ui.main.board

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.ReplyWrite
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.post.GetReply
import com.example.todaynan.data.remote.post.PostCommentList
import com.example.todaynan.data.remote.post.PostInterface
import com.example.todaynan.data.remote.post.PostList
import com.example.todaynan.data.remote.post.PostResponse
import com.example.todaynan.data.remote.post.Reply
import com.example.todaynan.databinding.FragmentPostBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.PostReplyRVAdapter
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostFragment : BaseFragment<FragmentPostBinding>(FragmentPostBinding::inflate){

    private val postService = getRetrofit().create(PostInterface::class.java)
    private var comment: String = ""
    private var gson: Gson = Gson()

    companion object {
        fun newInstance(text: String): PostFragment {
            val fragment = PostFragment()
            val args = Bundle()
            args.putString("type", text)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initAfterBinding() {
        val type = arguments?.getString("type")
        binding.postTypeTv.text = type

        binding.postBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val postJson = arguments?.getString("post")
        val post = gson.fromJson(postJson, PostList::class.java)
        setInit(post)

        val postId = arguments?.getInt("postId") ?: 0
        getReply(postId)

        binding.postRegisterBtnDark.setOnClickListener {
            val postId = arguments?.getInt("postId") ?: 0
            comment = binding.postReplyEt.text.toString() // 여기서 지정한 부분을 서버에 comment로 보내는 것
            replyWrite(postId, comment)
        }

    }

    private fun replyWrite(postId: Int, comment: String) {
        val replyWrite = ReplyWrite(comment)
        val request = "Bearer "+ AppData.appToken

        postService.reply(request, postId, replyWrite).enqueue(object :
            Callback<PostResponse<Reply>> {
            override fun onResponse(
                call: Call<PostResponse<Reply>>,
                response: Response<PostResponse<Reply>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())
                if (resp?.isSuccess == true) {
                    // 댓글 작성 성공 시, 댓글 목록 새로고침
                    getReply(postId)
                }
            }

            override fun onFailure(call: Call<PostResponse<Reply>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }
        })
    }

    private fun getReply(postId: Int) {
        val request = "Bearer "+AppData.appToken

        postService.getReply(request, postId).enqueue(object :
            Callback<PostResponse<GetReply>> {
            override fun onResponse(
                call: Call<PostResponse<GetReply>>,
                response: Response<PostResponse<GetReply>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())

                val items = resp?.result?.postCommentList?: emptyList()

                if (resp!!.isSuccess) {
                    val boardAdapter = PostReplyRVAdapter(items)
                    binding.postReplyRv.adapter = boardAdapter
                    binding.postReplyRv.layoutManager = LinearLayoutManager(context)
                    boardAdapter.setMyItemClickListener(object : PostReplyRVAdapter.MyItemClickListener {
                        override fun onItemClick(reply: PostCommentList) {

                        }
                    })
                }
            }

            override fun onFailure(call: Call<PostResponse<GetReply>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }
        })
    }

    private fun setInit(post: PostList){
        binding.postUserProfileIv.setImageResource(R.drawable.circle_green)
        binding.postUserNameTv.text = AppData.nickname
        binding.postUserLocTv.text = post.userAddress
        binding.postTitleTv.text = post.postTitle
        binding.postContentTv.text = post.postContent
        binding.postLikeNumberTv.text = post.postLike.toString()
        binding.postReplyNumberTv.text = post.postComment.toString()
        binding.postReplyNumberTv.text = post.postComment.toString()
    }
}