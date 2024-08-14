package com.example.todaynan.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.place.AddResult
import com.example.todaynan.data.remote.place.GeminiItem
import com.example.todaynan.data.remote.place.PlaceInterface
import com.example.todaynan.data.remote.place.PlaceRequest
import com.example.todaynan.data.remote.place.PlaceResponse
import com.example.todaynan.databinding.ItemRecommend1Binding
import com.example.todaynan.databinding.ItemRecommend2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsideRVAdapter(private val insideList: ArrayList<GeminiItem>?, private val type: Int) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val defaultItem = GeminiItem("시간을 달리는 소녀", "상세설명", "영화", R.drawable.item_temp_img.toString(), false)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (type == 1) {  // 나열형
            val binding: ItemRecommend1Binding = ItemRecommend1Binding
                .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            ListViewHolder(binding, viewGroup.context)
        } else {   // 블록형
            val binding: ItemRecommend2Binding = ItemRecommend2Binding
                .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            BlockViewHolder(binding, viewGroup.context)
        }
    }

    override fun getItemCount(): Int = insideList?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListViewHolder) {
            holder.bind(insideList?.get(position) ?: defaultItem)
        } else if (holder is BlockViewHolder) {
            holder.bind(insideList?.get(position) ?: defaultItem)
        }
    }

    //나열형 ViewHolder
    inner class ListViewHolder(private val binding: ItemRecommend1Binding, private val con: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(geminiItem: GeminiItem) {
            if(geminiItem.image != ""){
                setStringImage(geminiItem.image, binding.itemImageIv, con)
            }
            binding.itemTitleTv.text = geminiItem.title
            binding.itemCategoryTv.text = geminiItem.category
            binding.itemDetailTv.text = geminiItem.description
            if(geminiItem.isLike){
                binding.itemLikeOn.visibility = View.VISIBLE
                binding.itemLikeOff.visibility = View.INVISIBLE
            }else{
                binding.itemLikeOn.visibility = View.INVISIBLE
                binding.itemLikeOff.visibility = View.VISIBLE
            }

            val pInfo = PlaceRequest(
                title = geminiItem.title,
                description = geminiItem.description,
                placeAddress = geminiItem.category,
                image = geminiItem.image,
                category = "IN"
            )
            binding.itemLikeOff.setOnClickListener {
                binding.itemLikeOn.visibility = View.VISIBLE
                binding.itemLikeOff.visibility = View.INVISIBLE
                addLike(pInfo)
            }
        }
    }
    //블록형 ViewHolder
    inner class BlockViewHolder(private val binding: ItemRecommend2Binding, private val con: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(geminiItem: GeminiItem) {
            if(geminiItem.image != ""){
                setStringImage(geminiItem.image, binding.itemImageIv, con)
            }
            binding.itemTitleTv.text = geminiItem.title
            binding.itemInfoTv.text = geminiItem.category
        }
    }

    // 이미지뷰에 문자열 형태의 이미지를 설정
    fun setStringImage(imageUrl: String, imageView: ImageView, con: Context) {
        Glide.with(con)
            .load(imageUrl)
            .into(imageView)
    }

    fun addLike(pRequest: PlaceRequest){
        val placeService = getRetrofit().create(PlaceInterface::class.java)

        val auth = "Bearer "+ AppData.appToken
        placeService.addLike(auth, pRequest).enqueue(object: Callback<PlaceResponse<AddResult>> {
            override fun onResponse(
                call: Call<PlaceResponse<AddResult>>,
                response: Response<PlaceResponse<AddResult>>
            ) {
                Log.d("TAG_SUCCESS_add", response.toString())
                Log.d("TAG_SUCCESS_add", response.body().toString())
            }

            override fun onFailure(call: Call<PlaceResponse<AddResult>>, t: Throwable) {
                Log.d("TAG_FAIL_add", t.message.toString())
            }
        })
    }
}