package com.example.todaynan.data.remote.place

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlaceResponse<T> (
    @SerializedName(value="isSuccess")val isSuccess: Boolean,
    @SerializedName(value="code")val code: String,
    @SerializedName(value="message")val message: String,
    @SerializedName(value="result")val result: T,
): Serializable

// 안 놀거리 검색 응답값
data class Inside(
    @SerializedName(value="geminiResponseItemDTOList")val geminiResponseItemDTOList: ArrayList<GeminiItem>
)
data class GeminiItem(
    @SerializedName(value="title")val title: String,
    @SerializedName(value="description")val description: String,
    @SerializedName(value="category")val category: String,
    @SerializedName(value="image")val image: String,
    @SerializedName(value="isLike")var isLike: Boolean,
): Serializable

// 밖 놀거리 검색 응답값
data class Outside(
    @SerializedName(value="googlePlaceResultDTOList")val googlePlaceResultDTOList: ArrayList<GoogleItem>,
    @SerializedName(value="pageToken")val pageTokenizer: String,
)
data class GoogleItem(
    @SerializedName("geometry") val geometry: Geometry?,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("photoUrl") val photoUrl: String?,
    @SerializedName("type") val type: String,
    @SerializedName(value="isLike")var isLike: Boolean,
): Serializable
data class Geometry(
    @SerializedName("viewport") val viewport: Viewport,
)
data class Viewport(
    @SerializedName("low") val low: LatLng,
    @SerializedName("high") val high: LatLng
)
data class LatLng(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
)

// 좋아요 모아보기 응답값
data class LikeALL(
    @SerializedName("userLikeItems") val userLikeItems: List<LikeItem>
)
data class LikeItem(
    @SerializedName(value="like_id") val likeId: Int,
    @SerializedName(value="title") val title: String,
    @SerializedName(value="category") val category: String,
    @SerializedName(value="description") val description: String,
    @SerializedName(value="place_address") val placeAddress: String,
    @SerializedName(value="image") val image: String,
    @SerializedName(value="created_at") val createdAt: String,
    @SerializedName(value="updated_at") val updatedAt: String,
)

// 좋아요 등록 응답값
data class AddResult(
    @SerializedName(value="like_id") val likeId: Int,
    @SerializedName(value="title") val title: String,
    @SerializedName(value="created_at")val createdAt: String,
    @SerializedName(value="updated_at")val updatedAt: String,
)