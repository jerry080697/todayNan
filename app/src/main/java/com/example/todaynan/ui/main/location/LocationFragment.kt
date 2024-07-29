package com.example.todaynan.ui.main.location

import android.os.Bundle
import android.view.View
import com.example.todaynan.data.entity.LatLng
import com.example.todaynan.databinding.FragmentLocationBinding
import com.example.todaynan.ui.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null

    override fun initAfterBinding() {
        mapView = binding.mapView
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        currentMarker = setupMarker(LatLng(37.5562, 126.9724))  // default 서울역
        currentMarker?.showInfoWindow()
    }

    private fun setupMarker(locationLatLngEntity: LatLng): Marker? {
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
}
