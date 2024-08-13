package com.example.todaynan.ui.main.location

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.ChangeLocationRequest
import com.example.todaynan.data.entity.Place
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.user.ChangeLocationResponse
import com.example.todaynan.data.remote.user.GooglePlaceResultDTO
import com.example.todaynan.data.remote.user.LatLng
import com.example.todaynan.data.remote.user.SearchOutsideResponse
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.databinding.FragmentLocationBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.LocationPlaceRVAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationFragment : BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate), OnMapReadyCallback {
    private val userService = getRetrofit().create(UserInterface::class.java)
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null
    private lateinit var locationAdapter: LocationPlaceRVAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>
    private lateinit var locationSearchEt: EditText
    private lateinit var locationSearchBtn: View
    private lateinit var currentLocationTv: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        // Initialize BottomSheetBehavior
        val bottomSheet = view?.findViewById<NestedScrollView>(R.id.nested_scroll_view)
        bottomSheetBehavior = bottomSheet?.let { BottomSheetBehavior.from(it) }!!
        bottomSheetBehavior.peekHeight = 90
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        return view
    }

    override fun initAfterBinding() {
        mapView = binding.mapView
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)

        binding.currentLocationTv.setText(AppData.address)
//        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.location_recycler_view)
//        locationAdapter = LocationPlaceRVAdapter(generateDummyItems())
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter = locationAdapter

        locationSearchEt = binding.locationSearchEt
        locationSearchBtn = binding.locationSearchBtn
        currentLocationTv = binding.currentLocationTv
        locationSearchBtn.setOnClickListener {
            val searchText = locationSearchEt.text.toString()
            currentLocationTv.text = searchText
            searchLocations(searchText)
        }
        val initialSearchText = "${AppData.address} 놀거리와 먹거리"
        searchLocations(initialSearchText)
    }
    private fun searchLocations(searchText: String) {
        val accessToken = "Bearer ${AppData.appToken}"

        userService.searchOutside(accessToken, searchText, pageToken = "").enqueue(object : Callback<UserResponse<SearchOutsideResponse>> {
            override fun onResponse(
                call: Call<UserResponse<SearchOutsideResponse>>,
                response: Response<UserResponse<SearchOutsideResponse>>
            ) {
                Log.d("LocationFragment", "Response Code: ${response.code()}")
                Log.d("LocationFragment", "Response Body: ${response.body()}")
                Log.d("LocationFragment", "Response Message: ${response.message()}")

                if (response.isSuccessful) {
                    val userResponse = response.body()
                    if (userResponse?.isSuccess == true) {
                        val searchResponse = userResponse.result
                        if (searchResponse?.isSuccess == true) {
                            val places = searchResponse.result?.googlePlaceResultDTOList ?: emptyList()
                            Log.d("LocationFragment", "Places: $places")

                            if (places.isEmpty()) {
                                Toast.makeText(context, "검색된 장소가 없습니다.", Toast.LENGTH_SHORT).show()
                            } else {
                                updateMapMarkers(places)
                            }
                        } else {
                            Log.d("LocationFragment", "Search response failure: ${searchResponse?.message}")
                            Toast.makeText(context, "검색 응답 실패: ${searchResponse?.message}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("LocationFragment", "User response failure: ${userResponse?.message}")
                        Toast.makeText(context, "API 응답 실패: ${userResponse?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("LocationFragment", "Response error: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "서버와 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse<SearchOutsideResponse>>, t: Throwable) {
                Log.e("LocationFragment", "API 호출 실패: ${t.message}", t)
                Toast.makeText(context, "서버와 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun updateMapMarkers(places: List<GooglePlaceResultDTO>) {
        // 기존 마커 제거
        googleMap.clear()

        // 새로운 마커 추가
        for (place in places) {
            // 올바른 LatLng 값 사용
            val position = com.google.android.gms.maps.model.LatLng(
                place.geometry.viewport.low.lat,
                place.geometry.viewport.low.lng
            )

            val markerOptions = MarkerOptions().apply {
                position(position)
                title(place.name)
                snippet(place.address)
            }

            googleMap.addMarker(markerOptions)
        }

        // 첫 번째 장소로 카메라 이동
        if (places.isNotEmpty()) {
            val firstPlace = places[0]
            val firstPosition = com.google.android.gms.maps.model.LatLng(
                firstPlace.geometry.viewport.low.lat,
                firstPlace.geometry.viewport.low.lng
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPosition, 15f))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}