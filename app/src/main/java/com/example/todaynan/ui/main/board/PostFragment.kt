package com.example.todaynan.ui.main.board

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.ReplyWrite
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.post.DeletePost
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
        val postType = arguments?.getString("postType")

        if(postType == "구인 게시판"){
            binding.postTypeTv.text = "구인 게시판"
        } else {
            binding.postTypeTv.text = "잡담 게시판"
        }

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

        chooseType()
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
        val request = "Bearer ${AppData.appToken}"

        postService.getReply(request, postId).enqueue(object : Callback<PostResponse<GetReply>> {
            override fun onResponse(
                call: Call<PostResponse<GetReply>>,
                response: Response<PostResponse<GetReply>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())

                if (response.isSuccessful && resp != null) {
                    val items = resp.result?.postCommentList ?: emptyList()

                    if (resp.isSuccess) {
                        val boardAdapter = PostReplyRVAdapter(items)
                        binding.postReplyRv.adapter = boardAdapter
                        binding.postReplyRv.layoutManager = LinearLayoutManager(context)
                        boardAdapter.setMyItemClickListener(object : PostReplyRVAdapter.MyItemClickListener {
                            override fun onItemClick(reply: PostCommentList) {
                                // 아이템 클릭 시 처리 로직
                            }
                        })
                    } else {
                        // 응답이 성공했으나 isSuccess가 false인 경우의 처리
                        Log.d("SERVER/RESPONSE_ERROR", "Response was not successful: ${resp.message}")
                    }
                } else {
                    // 응답이 실패했거나 null인 경우의 처리
                    Log.d("SERVER/RESPONSE_ERROR", "Response was not successful or body was null")
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

    private fun chooseType(){
        binding.postPlusMenuNonIv.setOnClickListener{
            binding.postPlusMenuNonIv.setImageResource(R.drawable.plus_menu)
            val postNicknameJson = arguments?.getString("postNickname")

            if(AppData.nickname == postNicknameJson){ //닉네임 일치 시
                val typeList = mutableListOf<PostPopupValue>().apply {
                    add(PostPopupValue("신고"))
                    add(PostPopupValue("차단"))
                    add(PostPopupValue("삭제"))
                }
                PostMenuPopup(context = requireContext(), popupList = typeList){ _, _, position->
                    when (position) {
                        0 -> { //신고
                            Toast.makeText(requireContext(),"해당 게시글이 신고 처리 되었습니다", Toast.LENGTH_SHORT).show()
                        }

                        1 -> { //차단
                            Toast.makeText(requireContext(),"해당 게시글이 차단되었습니다", Toast.LENGTH_SHORT).show()
                        }

                        2 -> { //삭제
                            val request = "Bearer ${AppData.appToken}"
                            val postId = arguments?.getInt("postId") ?: 0

                            postService.deletePost(request, postId).enqueue(object :
                                Callback<DeletePost> {
                                override fun onResponse(
                                    call: Call<DeletePost>,
                                    response: Response<DeletePost>
                                ) {
                                    Log.d("SERVER/SUCCESS", response.toString())
                                    val resp = response.body()
                                    Log.d("SERVER/SUCCESS", resp.toString())

                                    if (resp?.isSuccess == true) {
                                        // 게시글 삭제 성공 시, 댓글 목록 새로고침 및 화면복귀
                                        getReply(postId)
                                        parentFragmentManager.popBackStack()
                                    }
                                }

                                override fun onFailure(call: Call<DeletePost>, t: Throwable) {
                                    Log.d("SERVER/FAILURE", t.message.toString())
                                }
                            })
                        }
                    }
                }.apply {
                    isOutsideTouchable = true
                    isTouchable = true
                    showAsDropDown(it, -270, 20) //resultMenuIv 기준으로 팝업메뉴 위치
                }
            } else{ //닉네임 불일치 시
                val typeList = mutableListOf<PostPopupValue>().apply {
                    add(PostPopupValue("신고"))
                    add(PostPopupValue("차단"))
                }
                PostMenuPopup(context = requireContext(), popupList = typeList){ _, _, position->
                    when (position) {
                        0 -> { //신고
                            Toast.makeText(requireContext(),"해당 게시글이 신고 처리 되었습니다", Toast.LENGTH_SHORT).show()
                        }

                        1 -> { //차단
                            Toast.makeText(requireContext(),"해당 게시글이 차단되었습니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.apply {
                    isOutsideTouchable = true
                    isTouchable = true
                    showAsDropDown(it, -270, 20) //resultMenuIv 기준으로 팝업메뉴 위치
                }
            }

        }
    }
}