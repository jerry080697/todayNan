package com.example.todaynan.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.R
import com.example.todaynan.data.entity.Chat
import com.example.todaynan.databinding.ItemChatBinding

class ChatRVAdapter(private val items: List<Chat>) : RecyclerView.Adapter<ChatRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding:ItemChatBinding = ItemChatBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.userName.text = item.userName
        holder.profileImg.setImageResource(R.drawable.default_profile_img)
        holder.content.text=item.content
        holder.transmissionTime.text=item.transmissionTime
        holder.isReadImg.setImageResource(R.drawable.red_dot)

        if (item.isRead) {
            holder.isReadImg.visibility = View.GONE
        } else {
            holder.isReadImg.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        val profileImg:ImageView=binding.postListUserProfileIv
        val userName: TextView = binding.postListUserNameTv
        val content:TextView=binding.postListContentTv
        val transmissionTime:TextView=binding.postListTransmissionTimeTv
        val isReadImg:ImageView=binding.postListIsReadIv
    }
}
