//package com.example.todaynan.ui.main.location
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.core.widget.NestedScrollView
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.todaynan.R
//import com.example.todaynan.base.AppData
//import com.example.todaynan.data.remote.getRetrofit
//import com.example.todaynan.data.remote.user.GooglePlaceResultDTO
//import com.example.todaynan.data.remote.user.SearchOutsideResult
//import com.example.todaynan.data.remote.user.UserInterface
//import com.example.todaynan.data.remote.user.UserResponse
//import com.example.todaynan.databinding.FragmentLocationBinding
//import com.example.todaynan.ui.BaseFragment
//import com.example.todaynan.ui.adapter.LocationPlaceRVAdapter
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.MapView
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.android.material.bottomsheet.BottomSheetBehavior
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class LocationFragment : BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate), OnMapReadyCallback {
//    private val userService = getRetrofit().create(UserInterface::class.java)
//    private lateinit var mapView: MapView
//    private lateinit var googleMap: GoogleMap
//    private lateinit var locationAdapter: LocationPlaceRVAdapter
//    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>
//    private lateinit var locationSearchEt: EditText
//    private lateinit var locationSearchBtn: View
//    private lateinit var currentLocationTv: TextView
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val view = super.onCreateView(inflater, container, savedInstanceState)
//
//        // Initialize BottomSheetBehavior
//        val bottomSheet = view?.findViewById<NestedScrollView>(R.id.nested_scroll_view)
//        bottomSheetBehavior = bottomSheet?.let { BottomSheetBehavior.from(it) }!!
//        bottomSheetBehavior.peekHeight = 90
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//
//        return view
//    }
//
//    override fun initAfterBinding() {
//        mapView = binding.mapView
//        mapView.onCreate(null)
//        mapView.onResume()
//        mapView.getMapAsync(this)
//
//        binding.currentLocationTv.setText(AppData.address)
//
//        locationSearchEt = binding.locationSearchEt
//        locationSearchBtn = binding.locationSearchBtn
//        currentLocationTv = binding.currentLocationTv
//        locationSearchBtn.setOnClickListener {
//            val searchText = locationSearchEt.text.toString()
//            currentLocationTv.text = searchText
//            searchLocations(searchText)
//        }
//        val initialSearchText = "${AppData.address} 놀거리와 먹거리"
//        searchLocations(initialSearchText)
//    }
//    private fun searchLocations(searchText: String) {
//        val accessToken = "Bearer ${AppData.appToken}"
//
//        userService.searchOutside(accessToken, searchText, pageToken = "").enqueue(object : Callback<UserResponse<SearchOutsideResult>> {
//            override fun onResponse(
//                call: Call<UserResponse<SearchOutsideResult>>,
//                response: Response<UserResponse<SearchOutsideResult>>
//            ) {
//                if (response.isSuccessful) {
//                    val userResponse = response.body()
//                    Log.d("LocationFragment", "Response Code: ${response.code()}")
//                    Log.d("LocationFragment", "Response Body: $userResponse")
//
//                    if (userResponse?.isSuccess == true) {
//                        val places = userResponse.result?.googlePlaceResultDTOList ?: emptyList()
//                        updateMapMarkers(places)
//                        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.location_recycler_view)
//                        recyclerView.layoutManager = LinearLayoutManager(context)
//                        locationAdapter = LocationPlaceRVAdapter(places) // Use GooglePlaceResultDTO
//                        binding.locationRecyclerView.adapter = locationAdapter
//                    } else {
//                        Log.d("LocationFragment", "Search response failure: ${userResponse?.message}")
//                        Toast.makeText(context, "장소 검색에 실패했습니다: ${userResponse?.message}", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    Log.d("LocationFragment", "Response error: ${response.errorBody()?.string()}")
//                    Toast.makeText(context, "서버 응답 오류", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<UserResponse<SearchOutsideResult>>, t: Throwable) {
//                Log.d("LocationFragment", "Request failed: ${t.message}")
//                Toast.makeText(context, "서버와 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//    private fun updateMapMarkers(places: List<GooglePlaceResultDTO>) {
//        // 기존 마커 제거
//        googleMap.clear()
//
//        // 새로운 마커 추가
//        for (place in places) {
//            val position = com.google.android.gms.maps.model.LatLng(
//                place.geometry.viewport.low.lat,
//                place.geometry.viewport.low.lng
//            )
//
//            val markerOptions = MarkerOptions().apply {
//                position(position)
//                title(place.name)
//                snippet(place.address)
//            }
//
//            googleMap.addMarker(markerOptions)
//        }
//
//        // 첫 번째 장소로 카메라 이동
//        if (places.isNotEmpty()) {
//            val firstPlace = places[0]
//            val firstPosition = com.google.android.gms.maps.model.LatLng(
//                firstPlace.geometry.viewport.low.lat,
//                firstPlace.geometry.viewport.low.lng
//            )
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPosition, 15f))
//        }
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        this.googleMap = googleMap
//    }
//    override fun onResume() {
//        super.onResume()
//        mapView.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mapView.onPause()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        mapView.onDestroy()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        mapView.onLowMemory()
//    }
//}
package com.example.todaynan.ui.main.location

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
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
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.user.GooglePlaceResultDTO
import com.example.todaynan.data.remote.user.SearchOutsideResult
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.databinding.FragmentLocationBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.LocationPlaceRVAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationFragment : BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate), OnMapReadyCallback {
    private val userService = getRetrofit().create(UserInterface::class.java)
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
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

        val addressParts = AppData.address.split(" ")
        if (addressParts.size > 2) {
            binding.locInfoTv.text = addressParts[2]
        } else {
            binding.locInfoTv.text = "없음" // 적절한 기본값 또는 에러 메시지
        }

        locationSearchEt = binding.locationSearchEt
        locationSearchBtn = binding.locationSearchBtn
        currentLocationTv = binding.locInfoTv
        locationSearchBtn.setOnClickListener {
            val searchText = locationSearchEt.text.toString()
            currentLocationTv.text = searchText
            searchLocations(searchText)
            hideKeyboard()
        }
        binding.locationSearchEt.setOnEditorActionListener { v, actionId, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                val searchText = locationSearchEt.text.toString()
                searchLocations(searchText)
                hideKeyboard()
                true // 이벤트 처리 완료
            } else {
                false // 이벤트 처리 안 함
            }
        }
        val initialSearchText = "${AppData.address} 놀거리와 먹거리"
        searchLocations(initialSearchText)
    }

    private fun searchLocations(searchText: String) {
        val accessToken = "Bearer ${AppData.appToken}"

        userService.searchOutside(accessToken, searchText, pageToken = "").enqueue(object : Callback<UserResponse<SearchOutsideResult>> {
            override fun onResponse(
                call: Call<UserResponse<SearchOutsideResult>>,
                response: Response<UserResponse<SearchOutsideResult>>
            ) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    Log.d("LocationFragment", "Response Code: ${response.code()}")
                    Log.d("LocationFragment", "Response Body: $userResponse")

                    if (userResponse?.isSuccess == true) {
                        val places = userResponse.result?.googlePlaceResultDTOList ?: emptyList()
                        updateMapMarkers(places)
                        locationAdapter = LocationPlaceRVAdapter(places, requireContext()) // Create adapter with context
                        binding.locationRecyclerView.layoutManager = LinearLayoutManager(context)
                        binding.locationRecyclerView.adapter = locationAdapter
                    } else {
                        Log.d("LocationFragment", "Search response failure: ${userResponse?.message}")
                        Toast.makeText(context, "장소 검색에 실패했습니다: ${userResponse?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("LocationFragment", "Response error: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "서버 응답 오류", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse<SearchOutsideResult>>, t: Throwable) {
                Log.d("LocationFragment", "Request failed: ${t.message}")
                Toast.makeText(context, "서버와 통신 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateMapMarkers(places: List<GooglePlaceResultDTO>) {
        // 기존 마커 제거
        googleMap.clear()

        // 새로운 마커 추가
        for (place in places) {
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
