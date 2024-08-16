package com.example.todaynan.ui.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.remote.post.PostCommentList
import com.example.todaynan.databinding.ItemReplyBinding

class PostReplyRVAdapter(private var replyData: List<PostCommentList>) : RecyclerView.Adapter<PostReplyRVAdapter.ViewHolder>() {

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
            binding.likedPostCreationTimeTv.text = "05.06. 14:40"
            binding.likedPostContentTv.text = reply.content
        }
    }


}