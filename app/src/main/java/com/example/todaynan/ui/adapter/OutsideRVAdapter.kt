package com.example.todaynan.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todaynan.base.AppData
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.place.AddResult
import com.example.todaynan.data.remote.place.GoogleItem
import com.example.todaynan.data.remote.place.PlaceInterface
import com.example.todaynan.data.remote.place.PlaceRequest
import com.example.todaynan.data.remote.place.PlaceResponse
import com.example.todaynan.databinding.ItemRecommend1Binding
import com.example.todaynan.databinding.ItemRecommend2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutsideRVAdapter(private var outsideList: ArrayList<GoogleItem>?, private val type: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val defaultItem = GoogleItem(null, "가천대학교", "경기도 성남시 수정구 성남대로 1342", null, "학교탐방", false)

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

    override fun getItemCount(): Int = outsideList?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListViewHolder) {
            holder.bind(outsideList?.get(position) ?: defaultItem)
        } else if (holder is BlockViewHolder) {
            holder.bind(outsideList?.get(position) ?: defaultItem)
        }
    }

    //나열형 ViewHolder
    inner class ListViewHolder(private val binding: ItemRecommend1Binding, private val con: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(googleItem: GoogleItem) {
            if(googleItem.photoUrl != ""){
                setStringImage(googleItem.photoUrl, binding.itemImageIv, con)
            }
            binding.itemTitleTv.text = googleItem.name
            binding.itemCategoryTv.text = googleItem.type
            binding.itemDetailTv.text = googleItem.address
            if(googleItem.isLike){
                binding.itemLikeOn.visibility = View.VISIBLE
                binding.itemLikeOff.visibility = View.INVISIBLE
            }else{
                binding.itemLikeOn.visibility = View.INVISIBLE
                binding.itemLikeOff.visibility = View.VISIBLE
            }

            val pInfo = PlaceRequest(
                title = googleItem.name,
                description = googleItem.type,
                placeAddress = googleItem.address,
                image = googleItem.photoUrl.toString(),
                category = "OUT"
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
        fun bind(googleItem: GoogleItem) {
            if(googleItem.photoUrl != ""){
                setStringImage(googleItem.photoUrl, binding.itemImageIv, con)
            }
            binding.itemTitleTv.text = googleItem.name
            binding.itemInfoTv.text = googleItem.type
        }
    }

    // 이미지뷰에 문자열 형태의 이미지를 설정
    fun setStringImage(imageUrl: String?, imageView: ImageView, con: Context) {
        Glide.with(con)
            .load(imageUrl)
            .into(imageView)
    }

    fun updateData(newItemList: ArrayList<GoogleItem>?) {
        outsideList = newItemList
        notifyDataSetChanged()  // 데이터 변경을 알림
    }

    fun addLike(pRequest: PlaceRequest){
        val placeService = getRetrofit().create(PlaceInterface::class.java)

        val auth = "Bearer "+AppData.appToken
        placeService.addLike(auth, pRequest).enqueue(object: Callback<PlaceResponse<AddResult>>{
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