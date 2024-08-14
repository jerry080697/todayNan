package com.example.todaynan.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.PlaceLikeRequest
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.user.GooglePlaceResultDTO
import com.example.todaynan.data.remote.user.PlaceLikeResponse
import com.example.todaynan.data.remote.user.PlaceLikeResult
import com.example.todaynan.data.remote.user.PlaceUnlikeResponse
import com.example.todaynan.data.remote.user.SearchOutsideResult
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.databinding.LocationPlaceItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationPlaceRVAdapter(private val places: List<GooglePlaceResultDTO>) : RecyclerView.Adapter<LocationPlaceRVAdapter.ViewHolder>() {

    private val userService = getRetrofit().create(UserInterface::class.java)

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

            // 좋아요 상태에 따라 UI 설정
            if (place.isLike) {
                binding.placeLikeOn.visibility = View.VISIBLE
                binding.placeLikeOff.visibility = View.GONE
            } else {
                binding.placeLikeOn.visibility = View.GONE
                binding.placeLikeOff.visibility = View.VISIBLE
            }

            // 좋아요 버튼 클릭 리스너 설정
            binding.placeLikeOff.setOnClickListener {
                place.isLike = true
                handleLikeClick(place)
            }

            binding.placeLikeOn.setOnClickListener {
                place.isLike = false
                place.likeId?.let { likeId ->
                    handleUnlikeClick(likeId)
                } ?: run {
                    Log.d("LocationPlaceRVAdapter", "No likeId found for the place")
                    Toast.makeText(itemView.context, "장소 ID를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun handleLikeClick(place: GooglePlaceResultDTO) {
            val request = PlaceLikeRequest(
                title = place.name,
                description = place.type,
                placeAddress = place.address,
                image = place.photoUrl,
                category = "IN"
            )

            userService.placeLike("Bearer ${AppData.appToken}", request).enqueue(object : Callback<UserResponse<PlaceLikeResult>> {
                override fun onResponse(call: Call<UserResponse<PlaceLikeResult>>, response: Response<UserResponse<PlaceLikeResult>>) {
                    if (response.isSuccessful) {
                        // 서버 응답에서 likeId를 업데이트
                        val likeId = response.body()?.result?.likeId
                        if (likeId != null) {
                            place.likeId = likeId // 업데이트
                            Log.d("LocationPlaceRVAdapter", "Place liked successfully with likeId: $likeId")
                        }

                        binding.placeLikeOn.visibility = View.VISIBLE
                        binding.placeLikeOff.visibility = View.GONE
                        Toast.makeText(itemView.context, "장소를 찜 목록에 추가했습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("LocationPlaceRVAdapter", "Failed to like place: ${response.body()?.message}")
                        Toast.makeText(itemView.context, "장소 찜에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserResponse<PlaceLikeResult>>, t: Throwable) {
                    Log.d("LocationPlaceRVAdapter", "Error occurred: ${t.message}")
                    Toast.makeText(itemView.context, "서버와의 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }


        private fun handleUnlikeClick(likeId: Int) {
            userService.placeUnlike("Bearer ${AppData.appToken}", likeId).enqueue(object : Callback<UserResponse<PlaceUnlikeResponse>> {
                override fun onResponse(call: Call<UserResponse<PlaceUnlikeResponse>>, response: Response<UserResponse<PlaceUnlikeResponse>>) {
                    if (response.isSuccessful && response.body()?.isSuccess == true) {
                        Log.d("LocationPlaceRVAdapter", "Place unliked successfully")
                        binding.placeLikeOn.visibility = View.GONE
                        binding.placeLikeOff.visibility = View.VISIBLE
                        Toast.makeText(itemView.context, "장소를 찜 목록에서 삭제했습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("LocationPlaceRVAdapter", "Failed to unlike place. Response code: ${response.code()}. Response body: ${response.body()}")
                        Toast.makeText(itemView.context, "장소 찜 해제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserResponse<PlaceUnlikeResponse>>, t: Throwable) {
                    Log.d("LocationPlaceRVAdapter", "Error occurred: ${t.message}")
                    Toast.makeText(itemView.context, "서버와의 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
