package com.example.todaynan.ui.main.board

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.ReplyWrite
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.post.DeletePost
import com.example.todaynan.data.remote.post.DeleteReply
import com.example.todaynan.data.remote.post.GetReply
import com.example.todaynan.data.remote.post.PostCommentList
import com.example.todaynan.data.remote.post.PostInterface
import com.example.todaynan.data.remote.post.PostLike
import com.example.todaynan.data.remote.post.PostList
import com.example.todaynan.data.remote.post.PostResponse
import com.example.todaynan.data.remote.post.Reply
import com.example.todaynan.data.remote.post.ReplyLike
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
            // 버튼 클릭 시 키보드 숨기기
            val inputMethodManager = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
            val currentFocusView = requireActivity().currentFocus
            currentFocusView?.let {
                inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
            }

            comment = binding.postReplyEt.text.toString() // 여기서 지정한 부분을 서버에 comment로 보냄
            replyWrite(postId, comment)
            binding.postReplyEt.setText("")
        }

        binding.postLikeIv.setOnClickListener {
            postLike(postId)
        }

        postMenu(post)
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
                        val boardAdapter = PostReplyRVAdapter(items,resp.result.postId)
                        //게시 작성자 프로필
                        val img =
                            when (resp.result.myPet) {
                                "DOG" -> R.drawable.fox_circle_off
                                "CAT" -> R.drawable.bird_circle_off
                                else -> R.drawable.bear_circle_off
                            }
                        binding.postUserProfileIv.setImageResource(img)
                        binding.postReplyRv.adapter = boardAdapter
                        binding.postReplyRv.layoutManager = LinearLayoutManager(context)
                        boardAdapter.setMyItemClickListener(object : PostReplyRVAdapter.MyItemClickListener {
                            override fun onItemClick(reply: PostCommentList) {
                                // 아이템 클릭 시 처리 로직
                            }

                            override fun onLikeBtnClick(reply: PostCommentList) {
                                // ImageView 클릭 시 처리 로직
                                val postCommentId = arguments?.getInt("postCommentId") ?: 0
                                replyLike(postId, postCommentId)
                            }

                            override fun onPlusBtnClick(reply: PostCommentList) {

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
        binding.postUserNameTv.text = post.userNickname
        binding.postUserLocTv.text = post.userAddress
        binding.postTitleTv.text = post.postTitle
        binding.postContentTv.text = post.postContent
        binding.postLikeNumberTv.text = post.postLike.toString()
        binding.postReplyNumberTv.text = post.postComment.toString()
        binding.postReplyNumberTv.text = post.postComment.toString()
        binding.postCreateTimeTv.text = post.createdAt
    }

    private fun postMenu(post: PostList){
        binding.postPlusMenuNonIv.setOnClickListener {
            binding.postPlusMenuNonIv.setImageResource(R.drawable.plus_menu)

            if (AppData.nickname == post.userNickname) { //닉네임 일치 시
                val popupView = layoutInflater.inflate((R.layout.popup_menu_my), null)
                val popupWindow = PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                // 팝업의 외곽을 클릭하면 닫히도록 설정
                popupWindow.isOutsideTouchable = true
                popupWindow.isFocusable = true
                // 팝업의 위치를 설정하여 화면에 표시
                val anchorView = binding.postPlusMenuNonIv // 기준 뷰
                popupWindow.showAsDropDown(anchorView, -270, 0)
                // 팝업 아이템 클릭 리스너 설정
                popupView.findViewById<ConstraintLayout>(R.id.menu_report_cl).setOnClickListener {
                    Toast.makeText(requireContext(), "해당 게시글이 신고 처리 되었습니다", Toast.LENGTH_SHORT)
                        .show()
                    popupWindow.dismiss()
                    binding.postPlusMenuNonIv.setImageResource(R.drawable.plus_menu_non)
                }
                popupView.findViewById<ConstraintLayout>(R.id.menu_block_cl).setOnClickListener {
                    Toast.makeText(requireContext(), "해당 게시글이 차단되었습니다", Toast.LENGTH_SHORT).show()
                    popupWindow.dismiss()
                    binding.postPlusMenuNonIv.setImageResource(R.drawable.plus_menu_non)
                }
                popupView.findViewById<ConstraintLayout>(R.id.menu_del_cl).setOnClickListener {
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
                                popupWindow.dismiss()
                                parentFragmentManager.popBackStack()
                            }
                        }

                        override fun onFailure(call: Call<DeletePost>, t: Throwable) {
                            Log.d("SERVER/FAILURE", t.message.toString())
                        }
                    })
                }
            } else { //닉네임 불일치 시
                val popupView = layoutInflater.inflate((R.layout.popup_menu_others), null)
                val popupWindow = PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                // 팝업의 외곽을 클릭하면 닫히도록 설정
                popupWindow.isOutsideTouchable = true
                popupWindow.isFocusable = true
                // 팝업의 위치를 설정하여 화면에 표시
                val anchorView = binding.postPlusMenuNonIv // 기준 뷰
                popupWindow.showAsDropDown(anchorView, -270, 0)
                // 팝업 아이템 클릭 리스너 설정
                popupView.findViewById<ConstraintLayout>(R.id.menu_others_report_cl).setOnClickListener {
                    Toast.makeText(requireContext(), "해당 게시글이 신고 처리 되었습니다", Toast.LENGTH_SHORT).show()
                    popupWindow.dismiss()
                    binding.postPlusMenuNonIv.setImageResource(R.drawable.plus_menu_non)
                }
                popupView.findViewById<ConstraintLayout>(R.id.menu_others_block_cl).setOnClickListener {
                    Toast.makeText(requireContext(), "해당 게시글이 차단되었습니다", Toast.LENGTH_SHORT).show()
                    popupWindow.dismiss()
                    binding.postPlusMenuNonIv.setImageResource(R.drawable.plus_menu_non)
                }
            }
        }
    }

    private fun postLike(postId: Int) {
        val request = "Bearer "+ AppData.appToken

        postService.postLike(request, postId).enqueue(object :
            Callback<PostResponse<PostLike>> {
            override fun onResponse(
                call: Call<PostResponse<PostLike>>,
                response: Response<PostResponse<PostLike>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())
                if (resp?.isSuccess == true) {
                    // 게시글 좋아요 성공 시, 좋아요 수 1증가
                    // 당장 눈에 보이는 뷰에서 1증가(어차피 나갔다가 들어오면 수 반영됨)
                    likePostConutPlus()
                }
            }

            override fun onFailure(call: Call<PostResponse<PostLike>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }
        })
    }

    private fun likePostConutPlus() {
        val likeCountText = binding.postLikeNumberTv.text.toString()
        val likeCount = likeCountText.toIntOrNull() ?: 0
        val updatedLikeCount = likeCount + 1
        binding.postLikeNumberTv.text = updatedLikeCount.toString()
    }

    private fun replyLike(postId: Int, commentId: Int) {
        val request = "Bearer "+ AppData.appToken

        postService.replyLike(request, postId, commentId).enqueue(object :
            Callback<PostResponse<ReplyLike>> {
            override fun onResponse(
                call: Call<PostResponse<ReplyLike>>,
                response: Response<PostResponse<ReplyLike>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())
                if (resp?.isSuccess == true) {
                    // 댓글 좋아요 성공 시, 하트 이미지 변경 및 좋아요 수 1증가
                    val popupView = layoutInflater.inflate(R.layout.item_reply, null)
                    popupView.findViewById<ImageView>(R.id.post_like_iv).setOnClickListener {
                        likeReplyConutPlus()
                    }
                }
            }

            override fun onFailure(call: Call<PostResponse<ReplyLike>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }
        })
    }

    private fun likeReplyConutPlus() {
        val likeCountTextView = binding.postLikeNumberTv

        val likeCountText = likeCountTextView.text.toString()
        val likeCount = likeCountText.toIntOrNull() ?: 0
        val updatedLikeCount = likeCount + 1

        likeCountTextView.text = updatedLikeCount.toString()
    }
}