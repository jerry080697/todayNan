package com.example.todaynan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todaynan.data.remote.user.UserLikeItem
import com.example.todaynan.databinding.ItemRecommend2Binding

class JjimListRVAdapter(private val items: List<UserLikeItem>) : RecyclerView.Adapter<JjimListRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRecommend2Binding = ItemRecommend2Binding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        Glide.with(holder.itemView.context)
            .load(item.image)
            .into(holder.profileImg)

        holder.title.text = item.title
        holder.address.text = item.placeAddress
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: ItemRecommend2Binding) : RecyclerView.ViewHolder(binding.root) {
        val profileImg: ImageView = binding.itemImageIv
        val title: TextView = binding.itemTitleTv
        val address: TextView = binding.itemInfoTv
    }
}
