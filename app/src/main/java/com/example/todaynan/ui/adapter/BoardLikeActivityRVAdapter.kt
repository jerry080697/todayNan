package com.example.todaynan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.R
import com.example.todaynan.data.entity.Post
import com.example.todaynan.databinding.ItemPostBinding

class BoardLikeActivityRVAdapter(private val items: List<Post>) : RecyclerView.Adapter<BoardLikeActivityRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding:ItemPostBinding = ItemPostBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.userName.text = item.userName
        holder.profileImg.setImageResource(R.drawable.default_profile_img)
        holder.userLocation.text = item.location
        //holder.boardTitle.text=item.boardTitle
        holder.creationTime.text= item.creationTime
        holder.likedNum.text= item.likedNum.toString()
        holder.replyNum.text=item.replyNum.toString()
        holder.postTitle.text=item.postTitle
        holder.postContent.text=item.postContent
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        val profileImg: ImageView = binding.likedPostProfileIv
        val userName: TextView = binding.likedPostUserNameTv
        val userLocation:TextView=binding.likedPostUserLocationTv
        val likedNum:TextView=binding.postLikeNumberTv
        val replyNum:TextView=binding.postReplyNumberTv
        val creationTime:TextView=binding.likedPostCreationTimeTv
        //val boardTitle:TextView=binding.likedPostBoardNameTv
        val postTitle:TextView=binding.likedPostTitleTv
        val postContent:TextView=binding.likedPostContentTv
    }
}
