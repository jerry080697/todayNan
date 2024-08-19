package com.example.todaynan.ui.adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.post.DeleteReply
import com.example.todaynan.data.remote.post.GetReply
import com.example.todaynan.data.remote.post.PostCommentList
import com.example.todaynan.data.remote.post.PostInterface
import com.example.todaynan.data.remote.post.PostResponse
import com.example.todaynan.data.remote.post.ReplyLike
import com.example.todaynan.databinding.ItemReplyBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostReplyRVAdapter(private var replyData: List<PostCommentList>, private var postId: Int) : RecyclerView.Adapter<PostReplyRVAdapter.ViewHolder>() {

    private val postService = getRetrofit().create(PostInterface::class.java)

    interface MyItemClickListener {
        fun onItemClick(reply: PostCommentList)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PostReplyRVAdapter.ViewHolder {
        val binding: ItemReplyBinding = ItemReplyBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostReplyRVAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            myItemClickListener.onItemClick(replyData[position])
        }

        holder.bind(replyData[position])
    }

    override fun getItemCount(): Int {
        return replyData.size
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
                    val items = resp.result?.postCommentList

                    if (resp.isSuccess) {
                        replyData = items!!
                        notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<PostResponse<GetReply>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }
        })
    }

    private fun replyLike(binding: ItemReplyBinding, postId: Int, commentId: Int) {
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
                    // 댓글 좋아요 성공 시, 좋아요 수 1증가
                    likeReplyConutPlus(binding)
                }
            }

            override fun onFailure(call: Call<PostResponse<ReplyLike>>, t: Throwable) {
                Log.d("SERVER/FAILURE", t.message.toString())
            }
        })
    }

    private fun likeReplyConutPlus(binding: ItemReplyBinding) {
        val likeCountText = binding.postLikeNumberTv.text.toString()
        val likeCount = likeCountText.toIntOrNull() ?: 0
        val updatedLikeCount = likeCount + 1
        binding.postLikeNumberTv.text = updatedLikeCount.toString()
    }

    inner class ViewHolder(val binding: ItemReplyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reply: PostCommentList) {
            // 사용자 ID로 이미지를 매핑하거나 서버에서 받아온 URL로 이미지를 로드하는 등의 방법을 사용
            val img =
                when (reply.myPet) {
                    "DOG" -> R.drawable.fox_circle_off
                    "CAT" -> R.drawable.bird_circle_off
                    else -> R.drawable.bear_circle_off
                }
            binding.postProfileIv.setImageResource(img) // 기본 이미지 설정
            binding.postUserNameTv.text = reply.nickName
            binding.postUserLocationTv.text = AppData.address
            binding.postLikeNumberTv.text = reply.postCommentLikeCnt.toString()
            binding.likedPostCreationTimeTv.text = reply.createdAt
            binding.likedPostContentTv.text = reply.content
            // 댓글 팝업 메뉴
            binding.postPlusMenuIv.setOnClickListener {
                if(AppData.nickname == reply.nickName){
                    val popupView = LayoutInflater.from(binding.root.context).inflate((R.layout.popup_menu_my), null)
                    val popupWindow = PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    // 팝업의 외곽을 클릭하면 닫히도록 설정
                    popupWindow.isOutsideTouchable = true
                    popupWindow.isFocusable = true
                    // 팝업의 위치를 설정하여 화면에 표시
                    val anchorView = binding.postPlusMenuIv // 기준 뷰
                    popupWindow.showAsDropDown(anchorView, -270, 0)
                    // 팝업 아이템 클릭 리스너 설정
                    popupView.findViewById<ConstraintLayout>(R.id.menu_report_cl).setOnClickListener {
                        Toast.makeText(binding.root.context, "해당 댓글이 신고 처리 되었습니다", Toast.LENGTH_SHORT)
                            .show()
                        popupWindow.dismiss()
                    }
                    popupView.findViewById<ConstraintLayout>(R.id.menu_block_cl).setOnClickListener {
                        Toast.makeText(binding.root.context, "해당 댓글이 차단되었습니다", Toast.LENGTH_SHORT).show()
                        popupWindow.dismiss()
                    }
                    popupView.findViewById<ConstraintLayout>(R.id.menu_del_cl).setOnClickListener {// 댓글 삭제
                        Toast.makeText(binding.root.context, "해당 댓글이 삭제되었습니다", Toast.LENGTH_SHORT).show()
                        popupWindow.dismiss()
                        val request = "Bearer ${AppData.appToken}"
                        val postCommentId = reply.postCommentId

                        postService.deleteReply(request, postId, postCommentId).enqueue(object :
                            Callback<DeleteReply> {
                            override fun onResponse(
                                call: Call<DeleteReply>,
                                response: Response<DeleteReply>
                            ) {
                                Log.d("SERVER/SUCCESS", response.toString())
                                val resp = response.body()
                                Log.d("SERVER/SUCCESS", resp.toString())

                                if (resp?.isSuccess == true) {
                                    // 댓글 삭제 성공 시, 댓글 목록 새로고침
                                    getReply(postId)
                                    popupWindow.dismiss()
                                }
                            }

                            override fun onFailure(call: Call<DeleteReply>, t: Throwable) {
                                Log.d("SERVER/FAILURE", t.message.toString())
                            }
                        })
                    }
                } else{
                    val popupView = LayoutInflater.from(binding.root.context).inflate((R.layout.popup_menu_others), null)
                    val popupWindow = PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    // 팝업의 외곽을 클릭하면 닫히도록 설정
                    popupWindow.isOutsideTouchable = true
                    popupWindow.isFocusable = true
                    // 팝업의 위치를 설정하여 화면에 표시
                    val anchorView = binding.postPlusMenuIv // 기준 뷰
                    popupWindow.showAsDropDown(anchorView, -270, 0)
                    // 팝업 아이템 클릭 리스너 설정
                    popupView.findViewById<ConstraintLayout>(R.id.menu_others_report_cl).setOnClickListener {
                        Toast.makeText(binding.root.context, "해당 댓글이 신고 처리 되었습니다", Toast.LENGTH_SHORT)
                            .show()
                        popupWindow.dismiss()
                    }
                    popupView.findViewById<ConstraintLayout>(R.id.menu_others_block_cl).setOnClickListener {
                        Toast.makeText(binding.root.context, "해당 댓글이 차단되었습니다", Toast.LENGTH_SHORT).show()
                        popupWindow.dismiss()
                    }
                }
            }

            binding.postLikeIv.setOnClickListener {
                replyLike(binding, postId, reply.postCommentId)
            }
        }
    }


}