package com.example.todaynan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.databinding.ItemRecommend1Binding

class RecommendRVAdapter(private val recommendList: ArrayList<Recommend>) :RecyclerView.Adapter<RecommendRVAdapter.ViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRecommend1Binding = ItemRecommend1Binding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int =recommendList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recommendList[position])
    }

    class ViewHolder(val binding: ItemRecommend1Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recommend: Recommend){
            binding.itemCategoryTv.text = recommend.category
            binding.itemImageIv.setImageResource(recommend.image!!)
            binding.itemTitleTv.text = recommend.title
            binding.itemInfoTv.text = recommend.info
            binding.itemDetailTv.text = recommend.detail
        }
    }

}