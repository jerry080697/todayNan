package com.example.todaynan

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.data.entity.MyLikedPost
import com.example.todaynan.databinding.MyLikedPostBinding

class BoardLikedRVAdapter(private val items: List<MyLikedPost>) : RecyclerView.Adapter<BoardLikedRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding:MyLikedPostBinding= MyLikedPostBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.userName.text = item.userName
        holder.profileImg.setImageResource(R.drawable.default_profile_img)
        holder.userLocation.text = item.location
        holder.boardTitle.text=item.boardTitle
        holder.creationTime.text= item.creationTime
        holder.likedNum.text= item.likedNum.toString()
        holder.replyNum.text=item.replyNum.toString()
        holder.postTitle.text=item.postTitle
        holder.postContent.text=item.postContent
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: MyLikedPostBinding) : RecyclerView.ViewHolder(binding.root) {
        val profileImg: ImageView = binding.likedPostProfileIv
        val userName: TextView = binding.likedPostUserNameTv
        val userLocation:TextView=binding.likedPostUserLocationTv
        val likedNum:TextView=binding.postLikeNumberTv
        val replyNum:TextView=binding.postReplyNumberTv
        val creationTime:TextView=binding.likedPostCreationTimeTv
        val boardTitle:TextView=binding.likedPostBoardNameTv
        val postTitle:TextView=binding.likedPostTitleTv
        val postContent:TextView=binding.likedPostContentTv
    }
}
