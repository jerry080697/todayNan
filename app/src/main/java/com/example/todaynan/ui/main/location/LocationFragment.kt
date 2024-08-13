package com.example.todaynan.ui.main.location

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.Place
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.user.GooglePlaceResultDTOList
import com.example.todaynan.data.remote.user.SearchOutsideResponse
import com.example.todaynan.data.remote.user.UserInterface
import com.example.todaynan.data.remote.user.UserResponse
import com.example.todaynan.databinding.FragmentLocationBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.LocationPlaceRVAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
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
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val TAG = "LocationFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.location_recycler_view)
        locationAdapter = LocationPlaceRVAdapter(generateDummyItems())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = locationAdapter

        locationSearchEt = binding.locationSearchEt
        locationSearchBtn = binding.locationSearchBtn
        currentLocationTv = binding.currentLocationTv
        binding.currentLocationTv.text = AppData.address

        locationSearchBtn.setOnClickListener {
            val searchText = locationSearchEt.text.toString()
            fetchPlacesFromApi(searchText)
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    private fun fetchPlacesFromApi(searchString: String) {
        val accessToken = "Bearer " + AppData.appToken
        val pageToken = ""

        userService.searchOutside(accessToken, searchString, pageToken).enqueue(object : Callback<UserResponse<SearchOutsideResponse>> {
            override fun onResponse(call: Call<UserResponse<SearchOutsideResponse>>, response: Response<UserResponse<SearchOutsideResponse>>) {
                if (response.isSuccessful) {
                    val searchOutsideResponse = response.body()
                    if (searchOutsideResponse != null) {
                        if (searchOutsideResponse.isSuccess) {
                            Log.d("MAP", "API Response: ${searchOutsideResponse.result}")
                            Log.d("MAP", "Places: ${searchOutsideResponse.result.result.googlePlaceResultDTOList}")
                            updateMapMarkers(searchOutsideResponse.result.result.googlePlaceResultDTOList)
                        } else {
                            Log.d("MAP", "API Error: ${searchOutsideResponse.message}")
                        }
                    } else {
                        Log.d("MAP", "API Response Body is null")
                    }
                } else {
                    Log.d("MAP", "API request failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserResponse<SearchOutsideResponse>>, t: Throwable) {
                Log.d("MAP", "Network error: ${t.message}")
            }
        })
    }



    private fun updateMapMarkers(places: List<GooglePlaceResultDTOList>) {
        googleMap.clear()
        places.forEach { place ->
            val position = LatLng(place.geometry.viewport.low.lat, place.geometry.viewport.low.lng)
            val markerOptions = MarkerOptions()
                .position(position)
                .title(place.name)
                .snippet(place.address)
            googleMap.addMarker(markerOptions)
            Log.d("MAP", "Added Marker: ${place.name} at $position")
        }
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    Log.d("MAP", "Current Location: Lat = ${location.latitude}, Lng = ${location.longitude}")
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                    googleMap.addMarker(MarkerOptions().position(currentLatLng).title("Current Location"))
                } else {
                    Log.d("MAP", "Unable to fetch current location")
                }
            }
        } else {
            Log.d("MAP", "Location permission is not granted")
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        // Attempt to get current location when the map is ready
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("MAP", "Location permission granted")
                getCurrentLocation()
            } else {
                Log.e("MAP", "Location permission denied")
            }
        }
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

    private fun generateDummyItems(): List<Place> {
        val items = ArrayList<Place>()
        items.add(Place("서울역", "서울 용산구 한강대로 405", "서울의 기차역", 37.554722, 126.970833, true, "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyNDA3MTJfNTkg%2FMDAxNzIwNzg4MDQ4NTY1.NjFBo1PR-NvFACZPM5V0ESP3RbT6IhHrQn8XPgoytncg.bEQme0xO6XBvZKP939ZQOjvkePYyXgfJf1oTsSAfjN8g.JPEG%2FIMG_6315.jpg&type=a340"))
        items.add(Place("용산역", "서울 용산구 한강대로 23길 55", "용산의 기차역", 37.5325, 126.965, false, "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyNDA3MTJfNTkg%2FMDAxNzIwNzg4MDQ4NTY1.NjFBo1PR-NvFACZPM5V0ESP3RbT6IhHrQn8XPgoytncg.bEQme0xO6XBvZKP939ZQOjvkePYyXgfJf1oTsSAfjN8g.JPEG%2FIMG_6315.jpg&type=a340"))
        items.add(Place("서울역", "서울 용산구 한강대로 405", "서울의 기차역", 37.554722, 126.970833, true, "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyNDA3MTJfNTkg%2FMDAxNzIwNzg4MDQ4NTY1.NjFBo1PR-NvFACZPM5V0ESP3RbT6IhHrQn8XPgoytncg.bEQme0xO6XBvZKP939ZQOjvkePYyXgfJf1oTsSAfjN8g.JPEG%2FIMG_6315.jpg&type=a340"))

        return items
    }
}