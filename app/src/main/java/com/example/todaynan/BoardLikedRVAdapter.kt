package com.example.todaynan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Date

class BoardLikedRVAdapter(private val items: List<MyLikedPost>) : RecyclerView.Adapter<BoardLikedRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_liked_post, parent, false)
        return ViewHolder(view)
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

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImg: ImageView = itemView.findViewById(R.id.liked_post_profile_iv)
        val userName: TextView = itemView.findViewById(R.id.liked_post_user_name_tv)
        val userLocation:TextView=itemView.findViewById(R.id.liked_post_user_location_tv)
        val likedNum:TextView=itemView.findViewById(R.id.post_like_number_tv)
        val replyNum:TextView=itemView.findViewById(R.id.post_reply_number_tv)
        val creationTime:TextView=itemView.findViewById(R.id.liked_post_creation_time_tv)
        val boardTitle:TextView=itemView.findViewById(R.id.liked_post_board_name_tv)
        val postTitle:TextView=itemView.findViewById(R.id.liked_post_title_tv)
    }
}
