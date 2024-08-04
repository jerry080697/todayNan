package com.example.todaynan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.data.entity.Post
import com.example.todaynan.databinding.ItemPostBinding

class PostRVAdapter(private val pList: List<Post>) : RecyclerView.Adapter<PostRVAdapter.ViewHolder>() {

    interface MyItemClickListner{
        fun onItemClick(post: Post)
    }
    private lateinit var myItemClickListner: MyItemClickListner
    fun setMyItemClickListner(itemClickListner: MyItemClickListner){
        myItemClickListner = itemClickListner
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPostBinding = ItemPostBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            myItemClickListner.onItemClick(pList[position])
        }
        holder.bind(pList[position])
    }

    override fun getItemCount(): Int {
        return pList.size
    }

    inner class ViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post){
            binding.postProfileIv.setImageResource(post.profileImg!!)
            binding.postUserNameTv.text = post.userName
            binding.postUserLocationTv.text = post.location
            binding.postLikeNumberTv.text = post.likedNum.toString()
            binding.postReplyNumberTv.text = post.replyNum.toString()
            binding.likedPostCreationTimeTv.text = post.creationTime
            binding.likedPostTitleTv.text = post.postTitle
            binding.likedPostContentTv.text = post.postContent
        }

    }
}
