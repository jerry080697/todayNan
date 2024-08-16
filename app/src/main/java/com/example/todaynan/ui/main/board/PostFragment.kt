package com.example.todaynan.ui.main.board
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.ReplyWrite
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.post.DeletePost
import com.example.todaynan.data.remote.post.GetReply
import com.example.todaynan.data.remote.post.LikePost
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
            // 버튼 클릭 시 키보드 숨기기
            val inputMethodManager = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
            val currentFocusView = requireActivity().currentFocus
            currentFocusView?.let {
                inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
            }

            val postId = arguments?.getInt("postId") ?: 0
            comment = binding.postReplyEt.text.toString() // 여기서 지정한 부분을 서버에 comment로 보냄
            replyWrite(postId, comment)
        }

        binding.postLikeIv.setOnClickListener {
            likePost(postId)    // 이미지 변경, 좋아요 수 최신화
            val postLikeCnt = arguments?.getInt("postLikeCnt") ?: 0
            binding.postLikeIv.visibility = View.INVISIBLE
            binding.postLikeFullIv.visibility = View.VISIBLE
            binding.postLikeNumberTv.text = postLikeCnt.toString()
            getReply(postId)
        }
        postMenu()
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
                        arguments = Bundle().apply{
                            val postLikeCnt = resp.result.postLikeCnt
                            putInt("postLikeCnt", postLikeCnt)
                        }
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
        val img =
            when (AppData.mypet) {
                "DOG" -> R.drawable.fox_circle_off
                "CAT" -> R.drawable.bird_circle_off
                else -> R.drawable.bear_circle_off
            }
        binding.postUserProfileIv.setImageResource(img)
        binding.postUserNameTv.text = post.userNickname
        binding.postUserLocTv.text = post.userAddress
        binding.postTitleTv.text = post.postTitle
        binding.postContentTv.text = post.postContent
        binding.postLikeNumberTv.text = post.postLike.toString()
        binding.postReplyNumberTv.text = post.postComment.toString()
        binding.postReplyNumberTv.text = post.postComment.toString()
        binding.postCreateTimeTv.text = post.createdAt
    }

    private fun postMenu(){
        binding.postPlusMenuNonIv.setOnClickListener {
            binding.postPlusMenuNonIv.setImageResource(R.drawable.plus_menu)
            val postNicknameJson = arguments?.getString("postNickname")

            if (AppData.nickname == postNicknameJson) { //닉네임 일치 시
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

    private fun likePost(postId: Int) {
        val request = "Bearer "+ AppData.appToken

        postService.likePost(request, postId).enqueue(object :
            Callback<PostResponse<LikePost>> {
            override fun onResponse(
                call: Call<PostResponse<LikePost>>,
                response: Response<PostResponse<LikePost>>
            ) {
                Log.d("SERVER/SUCCESS", response.toString())
                val resp = response.body()
                Log.d("SERVER/SUCCESS", resp.toString())
                if (resp?.isSuccess == true) {

                }
            }

            override fun onFailure(call: Call<PostResponse<LikePost>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }
        })
    }
}