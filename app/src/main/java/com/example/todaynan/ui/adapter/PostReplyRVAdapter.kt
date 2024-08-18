package com.example.todaynan.ui.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.remote.post.PostCommentList
import com.example.todaynan.databinding.ItemReplyBinding

class PostReplyRVAdapter(private var replyData: List<PostCommentList>) : RecyclerView.Adapter<PostReplyRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(reply: PostCommentList)
        fun onLikeBtnClick(reply: PostCommentList) // 추가된 인터페이스 메서드
        fun onPlusBtnClick(reply: PostCommentList) // 추가된 인터페이스 메서드
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
        holder.binding.postLikeIv.setOnClickListener { // ImageView 클릭 리스너 추가
            myItemClickListener.onLikeBtnClick(replyData[position])
            if(true) {
                holder.binding.postLikeIv.visibility = View.INVISIBLE
                holder.binding.postLikeFullIv.visibility = View.VISIBLE
                likeConutPlus(holder)
            }
        }
        holder.binding.postPlusMenuIv.setOnClickListener { // ImageView 클릭 리스너 추가
            myItemClickListener.onPlusBtnClick(replyData[position])
        }
        holder.bind(replyData[position])
    }

    private fun likeConutPlus(holder: PostReplyRVAdapter.ViewHolder) {
        val likeCountText = holder.binding.postLikeNumberTv.text.toString()
        val likeCount = likeCountText.toIntOrNull() ?: 0
        val updatedLikeCount = likeCount + 1
        holder.binding.postLikeNumberTv.text = updatedLikeCount.toString()
    }

    override fun getItemCount(): Int {
        return replyData.size
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
                    Toast.makeText(binding.root.context, "해당 게시글이 신고 처리 되었습니다", Toast.LENGTH_SHORT)
                        .show()
                    popupWindow.dismiss()
                }
                popupView.findViewById<ConstraintLayout>(R.id.menu_block_cl).setOnClickListener {
                    Toast.makeText(binding.root.context, "해당 게시글이 차단되었습니다", Toast.LENGTH_SHORT).show()
                    popupWindow.dismiss()
                }
                popupView.findViewById<ConstraintLayout>(R.id.menu_del_cl).setOnClickListener {// 댓글 삭제
                    Toast.makeText(binding.root.context, "해당 게시글이 삭제되었습니다", Toast.LENGTH_SHORT).show()
                    popupWindow.dismiss()
                }
            }
        }
    }


}