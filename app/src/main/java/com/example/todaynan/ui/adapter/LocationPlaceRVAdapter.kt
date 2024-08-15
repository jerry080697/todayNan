//package com.example.todaynan.ui.adapter
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.todaynan.base.AppData
//import com.example.todaynan.data.entity.PlaceLikeRequest
//import com.example.todaynan.data.remote.getRetrofit
//import com.example.todaynan.data.remote.user.GooglePlaceResultDTO
//import com.example.todaynan.data.remote.user.PlaceLikeResult
//import com.example.todaynan.data.remote.user.PlaceUnlikeResponse
//import com.example.todaynan.data.remote.user.UserInterface
//import com.example.todaynan.data.remote.user.UserResponse
//import com.example.todaynan.databinding.LocationPlaceItemBinding
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class LocationPlaceRVAdapter(private val places: List<GooglePlaceResultDTO>) : RecyclerView.Adapter<LocationPlaceRVAdapter.ViewHolder>() {
//
//    private val userService = getRetrofit().create(UserInterface::class.java)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = LocationPlaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(places[position])
//    }
//
//    override fun getItemCount(): Int = places.size
//
//    inner class ViewHolder(private val binding: LocationPlaceItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(place: GooglePlaceResultDTO) {
//            binding.placeNameTv.text = place.name
//            binding.placeAddressTv.text = place.address
//            binding.placeDetailTv.text = place.type
//
//            Glide.with(itemView)
//                .load(place.photoUrl)
//                .into(binding.placeImgIv)
//
//            if (place.isLike) {
//                binding.placeLikeOn.visibility = View.VISIBLE
//                binding.placeLikeOff.visibility = View.GONE
//            } else {
//                binding.placeLikeOn.visibility = View.GONE
//                binding.placeLikeOff.visibility = View.VISIBLE
//            }
//            binding.placeLikeOff.setOnClickListener {
//                place.isLike = true
//                handleLikeClick(place)
//            }
//
//            binding.placeLikeOn.setOnClickListener {
//                place.isLike = false
//                place.likeId?.let { likeId ->
//                    handleUnlikeClick(likeId)
//                } ?: run {
//                    Log.d("LocationPlaceRVAdapter", "No likeId found for the place")
//                    Toast.makeText(itemView.context, "장소 ID를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//        private fun handleLikeClick(place: GooglePlaceResultDTO) {
//            val request = PlaceLikeRequest(
//                title = place.name,
//                description = place.type,
//                placeAddress = place.address,
//                image = place.photoUrl,
//                category = "IN"
//            )
//
//            userService.placeLike("Bearer ${AppData.appToken}", request).enqueue(object : Callback<UserResponse<PlaceLikeResult>> {
//                override fun onResponse(call: Call<UserResponse<PlaceLikeResult>>, response: Response<UserResponse<PlaceLikeResult>>) {
//                    if (response.isSuccessful) {
//                        val likeId = response.body()?.result?.likeId
//                        if (likeId != null) {
//                            place.likeId = likeId
//                            Log.d("LocationPlaceRVAdapter", "Place liked successfully with likeId: $likeId")
//                        }
//
//                        binding.placeLikeOn.visibility = View.VISIBLE
//                        binding.placeLikeOff.visibility = View.GONE
//                        Toast.makeText(itemView.context, "장소를 찜 목록에 추가했습니다.", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Log.d("LocationPlaceRVAdapter", "Failed to like place: ${response.body()?.message}")
//                        Toast.makeText(itemView.context, "장소 찜에 실패했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<UserResponse<PlaceLikeResult>>, t: Throwable) {
//                    Log.d("LocationPlaceRVAdapter", "Error occurred: ${t.message}")
//                    Toast.makeText(itemView.context, "서버와의 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                }
//            })
//        }
//
//
//        private fun handleUnlikeClick(likeId: Int) {
//            userService.placeUnlike("Bearer ${AppData.appToken}", likeId).enqueue(object : Callback<UserResponse<PlaceUnlikeResponse>> {
//                override fun onResponse(call: Call<UserResponse<PlaceUnlikeResponse>>, response: Response<UserResponse<PlaceUnlikeResponse>>) {
//                    if (response.isSuccessful && response.body()?.isSuccess == true) {
//                        Log.d("LocationPlaceRVAdapter", "Place unliked successfully")
//                        binding.placeLikeOn.visibility = View.GONE
//                        binding.placeLikeOff.visibility = View.VISIBLE
//                        Toast.makeText(itemView.context, "장소를 찜 목록에서 삭제했습니다.", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Log.d("LocationPlaceRVAdapter", "Failed to unlike place. Response code: ${response.code()}. Response body: ${response.body()}")
//                        Toast.makeText(itemView.context, "장소 찜 해제에 실패했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<UserResponse<PlaceUnlikeResponse>>, t: Throwable) {
//                    Log.d("LocationPlaceRVAdapter", "Error occurred: ${t.message}")
//                    Toast.makeText(itemView.context, "서버와의 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                }
//            })
//        }
//    }
//}


package com.example.todaynan.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.PlaceLikeRequest
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.place.LikeALL
import com.example.todaynan.data.remote.place.LikeItem
import com.example.todaynan.data.remote.place.PlaceInterface
import com.example.todaynan.data.remote.place.PlaceResponse
import com.example.todaynan.data.remote.user.GooglePlaceResultDTO
import com.example.todaynan.data.remote.user.PlaceLikeResult
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.databinding.LocationPlaceItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationPlaceRVAdapter(
    private val places: List<GooglePlaceResultDTO>,
    context: Context
) : RecyclerView.Adapter<LocationPlaceRVAdapter.ViewHolder>() {

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
            // binding.placeDetailTv.text = place.type

            Glide.with(itemView)
                .load(place.photoUrl)
                .into(binding.placeImgIv)

            if(place.isLike){
                binding.placeLikeOn.visibility = View.VISIBLE
                binding.placeLikeOff.visibility = View.INVISIBLE
            } else{
                binding.placeLikeOn.visibility = View.INVISIBLE
                binding.placeLikeOff.visibility = View.VISIBLE
            }

            binding.placeLikeOff.setOnClickListener {
                place.isLike = true
                binding.placeLikeOn.visibility = View.VISIBLE
                binding.placeLikeOff.visibility = View.INVISIBLE
                handleLikeClick(place)
            }

            binding.placeLikeOn.setOnClickListener {
                place.isLike = false
                binding.placeLikeOn.visibility = View.INVISIBLE
                binding.placeLikeOff.visibility = View.VISIBLE
                handleUnlikeClick(place)
            }
        }

        private fun updateLikeVisibility(isLiked: Boolean) {
            if (isLiked) {
                binding.placeLikeOn.visibility = View.VISIBLE
                binding.placeLikeOff.visibility = View.GONE
            } else {
                binding.placeLikeOn.visibility = View.GONE
                binding.placeLikeOff.visibility = View.VISIBLE
            }
        }

        private fun handleLikeClick(place: GooglePlaceResultDTO) {
            val request = PlaceLikeRequest(
                title = place.name,
                description = place.type,
                placeAddress = place.address,
                image = place.photoUrl,
                category = "OUT"
            )

            userService.placeLike("Bearer ${AppData.appToken}", request).enqueue(object : Callback<UserResponse<PlaceLikeResult>> {
                override fun onResponse(call: Call<UserResponse<PlaceLikeResult>>, response: Response<UserResponse<PlaceLikeResult>>) {
                    if (response.isSuccessful) {
                        val likeId = response.body()?.result?.likeId
                        if (likeId != null) {
                            place.likeId = likeId
                            Log.d("LocationPlaceRVAdapter", "Place liked successfully with likeId: $likeId")
                        }

                        updateLikeVisibility(true)
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

        fun allLike(callback: OutsideRVAdapter.LikeIdCallback){
            val placeService = getRetrofit().create(PlaceInterface::class.java)

            placeService.listLike("Bearer ${AppData.appToken}").enqueue(object: Callback<PlaceResponse<LikeALL>>{
                override fun onResponse(
                    call: Call<PlaceResponse<LikeALL>>,
                    response: Response<PlaceResponse<LikeALL>>
                ) {
                    Log.d("TAG_SUCCESS_list", response.toString())
                    Log.d("TAG_SUCCESS_list", response.body().toString())

                    response.body()?.let { placeResponse ->
                        callback.onLikeIdReceived(placeResponse.result.userLikeItems) // result는 ArrayList<LikeItem> 타입이라고 가정
                    }
                }

                override fun onFailure(call: Call<PlaceResponse<LikeALL>>, t: Throwable) {
                    Log.d("TAG_FAIL_add", t.message.toString())
                }
            })
        }
        private fun handleUnlikeClick(place: GooglePlaceResultDTO) {
            allLike(object : OutsideRVAdapter.LikeIdCallback {
                override fun onLikeIdReceived(likeItems: List<LikeItem>) {
                    // likeItems에서 GooglePlaceResultDTO와 매칭되는 likeId를 찾음
                    val likeItem = likeItems.find { it.title == place.name }
                    likeItem?.likeId?.let { likeId ->
                        // likeId 존재할 경우 좋아요 삭제 진행
                        userService.placeUnlike("Bearer ${AppData.appToken}", likeId).enqueue(object: Callback<UserResponse<String>> {
                            override fun onResponse(
                                call: Call<UserResponse<String>>,
                                response: Response<UserResponse<String>>
                            ) {
                                Log.d("TAG_SUCCESS_del", response.toString())
                                Log.d("TAG_SUCCESS_del", response.body().toString())

                                Toast.makeText(itemView.context, "장소를 찜 목록에서 삭제했습니다.", Toast.LENGTH_SHORT).show()
                            }

                            override fun onFailure(call: Call<UserResponse<String>>, t: Throwable) {
                                Log.d("TAG_FAIL_del", t.message.toString())
                            }
                        })
                    }
                }
            })
        }

    }
}
