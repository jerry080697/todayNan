package com.example.todaynan.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.data.entity.City
import com.example.todaynan.data.entity.Location
import com.example.todaynan.databinding.CityItemBinding
import com.example.todaynan.databinding.DistrictItemBinding
import com.example.todaynan.databinding.DongItemBinding

class CityRVAdapter(
    private val items: List<City>,
    private val onItemClick: (City) -> Unit
) : RecyclerView.Adapter<CityRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = CityItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.cityName.text = item.cityName
        holder.itemView.setOnClickListener { onItemClick(item) }
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

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = DistrictItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.districtName.text = item.districtName
        holder.itemView.setOnClickListener { onItemClick(item) }
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

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = DongItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.dongName.text = item.dongName
        holder.itemView.setOnClickListener { onItemClick(item) } // Set the click listener
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: DongItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val dongName: TextView = binding.dongNameTv
    }
}

