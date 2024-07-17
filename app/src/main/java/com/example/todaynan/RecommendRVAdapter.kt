package com.example.todaynan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.databinding.ItemRecommend1Binding
import com.example.todaynan.databinding.ItemRecommend2Binding

class RecommendRVAdapter(private val recommendList: ArrayList<Recommend>, private val type: Int) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (type == 1) {  // 나열형
            val binding: ItemRecommend1Binding = ItemRecommend1Binding
                .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            ListViewHolder(binding)
        } else {   // 블록형
            val binding: ItemRecommend2Binding = ItemRecommend2Binding
                .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            BlockViewHolder(binding)
        }
    }

    override fun getItemCount(): Int =recommendList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListViewHolder) {
            holder.bind(recommendList[position])
        } else if (holder is BlockViewHolder) {
            holder.bind(recommendList[position])
        }
    }

    //나열형 ViewHolder
    class ListViewHolder(private val binding: ItemRecommend1Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recommend: Recommend) {
            binding.itemImageIv.setImageResource(recommend.image!!)
            binding.itemTitleTv.text = recommend.title
            binding.itemCategoryTv.text = recommend.category
            binding.itemInfoTv.text = recommend.info
            binding.itemDetailTv.text = recommend.detail
        }
    }
    //블록형 ViewHolder
    class BlockViewHolder(private val binding: ItemRecommend2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recommend: Recommend) {
            binding.itemImageIv.setImageResource(recommend.image!!)
            binding.itemTitleTv.text = recommend.title
        }
    }

}