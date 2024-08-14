package com.example.todaynan.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todaynan.R
import com.example.todaynan.data.remote.user.GooglePlaceResultDTO
import com.example.todaynan.databinding.LocationPlaceItemBinding

class LocationPlaceRVAdapter(private val places: List<GooglePlaceResultDTO>) : RecyclerView.Adapter<LocationPlaceRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LocationPlaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount(): Int = places.size

    inner class ViewHolder(private val binding: LocationPlaceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: GooglePlaceResultDTO) {
            binding.placeNameTv.text = place.name
            binding.placeAddressTv.text = place.address
            binding.placeDetailTv.text = place.type


            Glide.with(itemView)
                .load(place.photoUrl)
                .into(binding.placeImgIv)

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
