package com.example.todaynan.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todaynan.data.remote.user.UserLikeItem
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.data.remote.user.PlaceUnlikeResponse
import com.example.todaynan.databinding.ItemRecommend2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.todaynan.base.AppData
import android.widget.Toast
import com.example.todaynan.data.remote.getRetrofit

class JjimListRVAdapter(private val items: MutableList<UserLikeItem>) : RecyclerView.Adapter<JjimListRVAdapter.ViewHolder>() {
    private val userService = getRetrofit().create(UserInterface::class.java)
    private val accessToken = "Bearer ${AppData.appToken}"

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

        holder.itemLikeOff.visibility = View.INVISIBLE
        holder.itemLikeOn.visibility = View.VISIBLE
        holder.itemLikeOn.setOnClickListener {
            handleUnlikeClick(item.likeId, position, holder)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun handleUnlikeClick(likeId: Int, position: Int, holder: ViewHolder) {
        userService.placeUnlike(accessToken, likeId).enqueue(object : Callback<UserResponse<String>> {
            override fun onResponse(call: Call<UserResponse<String>>, response: Response<UserResponse<String>>) {
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    // 로그에 삭제된 likeId 출력
                    Log.d("JjimListRVAdapter", "아이템 삭제됨: likeId = $likeId")

                    // 아이템 삭제 후 전체 리스트 갱신
                    items.removeAt(position)
                    notifyDataSetChanged() // 전체 데이터 갱신

                    Toast.makeText(holder.itemView.context, "장소 찜 목록에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(holder.itemView.context, "장소 찜 해제에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse<String>>, t: Throwable) {
                Toast.makeText(holder.itemView.context, "서버 통신 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }

    inner class ViewHolder(val binding: ItemRecommend2Binding) : RecyclerView.ViewHolder(binding.root) {
        val profileImg: ImageView = binding.itemImageIv
        val title: TextView = binding.itemTitleTv
        val address: TextView = binding.itemInfoTv
        val itemLikeOn: ImageView = binding.itemLikeOn
        val itemLikeOff: ImageView = binding.itemLikeOff
    }
}
