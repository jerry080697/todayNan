package com.example.todaynan.ui.main.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.R
import com.example.todaynan.data.entity.Place
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

class LocationFragment : BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null
    private lateinit var locationAdapter: LocationPlaceRVAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>

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

        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.location_recycler_view)
        locationAdapter = LocationPlaceRVAdapter(generateDummyItems())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = locationAdapter
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        currentMarker = setupMarker(Place("서울역", "서울특별시 용산구 한강대로 405", "서울역 설명~", 37.554722, 126.970833, true, R.drawable.default_profile_img))
        currentMarker?.showInfoWindow()
    }

    private fun setupMarker(locationLatLngEntity: Place): Marker? {
        val latitude = locationLatLngEntity.latitude ?: return null
        val longitude = locationLatLngEntity.longitude ?: return null

        val positionLatLng = com.google.android.gms.maps.model.LatLng(latitude, longitude)
        val markerOption = MarkerOptions().apply {
            position(positionLatLng)
            title("위치")
            snippet("서울역 위치")
        }

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 15f))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
        return googleMap.addMarker(markerOption)
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
        items.add(Place("서울역", "서울 용산구 한강대로 405", "서울의 기차역", 37.554722, 126.970833, true, R.drawable.default_profile_img))
        items.add(Place("용산역", "서울 용산구 한강대로 23길 55", "용산의 기차역", 37.5325, 126.965, false, R.drawable.default_profile_img))
        items.add(Place("서울역", "서울 용산구 한강대로 405", "서울의 기차역", 37.554722, 126.970833, true, R.drawable.default_profile_img))
        items.add(Place("용산역", "서울 용산구 한강대로 23길 55", "용산의 기차역", 37.5325, 126.965, false, R.drawable.default_profile_img))
        items.add(Place("서울역", "서울 용산구 한강대로 405", "서울의 기차역", 37.554722, 126.970833, true, R.drawable.default_profile_img))
        items.add(Place("용산역", "서울 용산구 한강대로 23길 55", "용산의 기차역", 37.5325, 126.965, false, R.drawable.default_profile_img))
        items.add(Place("서울역", "서울 용산구 한강대로 405", "서울의 기차역", 37.554722, 126.970833, true, R.drawable.default_profile_img))
        items.add(Place("용산역", "서울 용산구 한강대로 23길 55", "용산의 기차역", 37.5325, 126.965, false, R.drawable.default_profile_img))


        return items
    }
}
