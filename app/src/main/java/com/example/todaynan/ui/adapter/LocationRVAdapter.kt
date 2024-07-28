package com.example.todaynan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.R
import com.example.todaynan.data.entity.City
import com.example.todaynan.data.entity.Location
import com.example.todaynan.databinding.CityItemBinding
import com.example.todaynan.databinding.DistrictItemBinding
import com.example.todaynan.databinding.DongItemBinding

class CityRVAdapter(
    private val items: List<City>,
    private val onItemClick: (City) -> Unit
) : RecyclerView.Adapter<CityRVAdapter.ViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = CityItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.cityName.text = item.cityName

        // 아이템 클릭 시 동작 설정
        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.bindingAdapterPosition
            onItemClick(item)
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
        // 클릭된 아이템의 배경색 변경
        if (position == selectedPosition) {
            holder.itemView.setBackgroundResource(R.drawable.item_border_no_radius_selected)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.item_border_no_radius)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: CityItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val cityName: TextView = binding.cityNameTv
    }
}
class DistrictRVAdapter(
    private val items: List<Location>,
    private val onItemClick: (Location) -> Unit
) : RecyclerView.Adapter<DistrictRVAdapter.ViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = DistrictItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.districtName.text = item.districtName

        // 아이템 클릭 시 동작 설정
        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.bindingAdapterPosition
            onItemClick(item)
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
        // 클릭된 아이템의 배경색 변경
        if (position == selectedPosition) {
            holder.itemView.setBackgroundResource(R.drawable.item_border_no_radius_selected)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.item_border_no_radius)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: DistrictItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val districtName: TextView = binding.districtNameTv
    }
}
class DongRVAdapter(
    private val items: List<Location>,
    private val onItemClick: (Location) -> Unit
) : RecyclerView.Adapter<DongRVAdapter.ViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = DongItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.dongName.text = item.dongName

        // 아이템 클릭 시 동작 설정
        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.bindingAdapterPosition
            onItemClick(item)
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
        // 클릭된 아이템의 배경색 변경
        if (position == selectedPosition) {
            holder.itemView.setBackgroundResource(R.drawable.item_border_no_radius_selected)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.item_border_no_radius)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: DongItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val dongName: TextView = binding.dongNameTv
    }
}

