package com.example.todaynan.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todaynan.R
import com.example.todaynan.data.entity.Place
import com.example.todaynan.databinding.LocationPlaceItemBinding

class LocationPlaceRVAdapter(private val places: List<Place>) : RecyclerView.Adapter<LocationPlaceRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LocationPlaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount(): Int = places.size

    inner class ViewHolder(private val binding: LocationPlaceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Place) {
            binding.placeNameTv.text = place.placeName
            binding.placeAddressTv.text = place.placeAddress
            binding.placeDetailTv.text = place.placeDescription

            Glide.with(itemView).load("https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyNDA3MTJfNTkg%2FMDAxNzIwNzg4MDQ4NTY1.NjFBo1PR-NvFACZPM5V0ESP3RbT6IhHrQn8XPgoytncg.bEQme0xO6XBvZKP939ZQOjvkePYyXgfJf1oTsSAfjN8g.JPEG%2FIMG_6315.jpg&type=a340").into(binding.placeImgIv)
            if (place.isLike) {
                binding.placeLikeOn.visibility = View.VISIBLE
                binding.placeLikeOff.visibility = View.GONE
            } else {
                binding.placeLikeOn.visibility = View.GONE
                binding.placeLikeOff.visibility = View.VISIBLE
            }
        }
    }
}