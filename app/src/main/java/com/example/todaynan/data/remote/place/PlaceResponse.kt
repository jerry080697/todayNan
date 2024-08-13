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

data class Inside(
    @SerializedName(value="geminiResponseItemDTOList")val geminiResponseItemDTOList: ArrayList<GeminiItem>
)
data class GeminiItem(
    @SerializedName(value="title")val title: String,
    @SerializedName(value="description")val description: String,
    @SerializedName(value="category")val category: String,
    @SerializedName(value="image")val image: String,
    @SerializedName(value="isLike")var isLike: Boolean,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun describeContents(): Int {
        return 0
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(category)
        parcel.writeString(image)
        parcel.writeByte(if (isLike) 1 else 0)
    }
    companion object CREATOR : Parcelable.Creator<GeminiItem> {
        override fun createFromParcel(parcel: Parcel): GeminiItem {
            return GeminiItem(parcel)
        }
        override fun newArray(size: Int): Array<GeminiItem?> {
            return arrayOfNulls(size)
        }
    }
}

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