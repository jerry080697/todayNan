package com.example.todaynan.ui.main.search

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.data.entity.City
import com.example.todaynan.data.entity.Location
import com.example.todaynan.databinding.FragmentSearchLocationBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.CityRVAdapter
import com.example.todaynan.ui.adapter.DistrictRVAdapter
import com.example.todaynan.ui.adapter.DongRVAdapter
import com.example.todaynan.utils.CSVUtils

class SearchLocationFragment : BaseFragment<FragmentSearchLocationBinding>(FragmentSearchLocationBinding::inflate) {

    private var selectedCity: String? = null
    private var selectedDistrict: String? = null
    private var selectedDong: String? = null

    override fun initAfterBinding() {
        val allLocations = generateDummyItems()
        val cities = generateDummyItem()

        val cityAdapter = CityRVAdapter(cities) { city ->
            onCityItemClick(city, allLocations)
        }
        binding.searchLocCityRv.adapter = cityAdapter
        binding.searchLocCityRv.layoutManager = LinearLayoutManager(requireContext())

        val districtAdapter = DistrictRVAdapter(emptyList()) { location ->
            onDistrictItemClick(location, allLocations)
        }
        binding.searchLocDistrictRv.adapter = districtAdapter
        binding.searchLocDistrictRv.layoutManager = LinearLayoutManager(requireContext())

        val dongAdapter = DongRVAdapter(emptyList()) { location ->
            onDongItemClick(location)
        }
        binding.searchLocDongRv.adapter = dongAdapter
        binding.searchLocDongRv.layoutManager = LinearLayoutManager(requireContext())

        binding.searchLocBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun onCityItemClick(city: City, allLocations: List<Location>) {
        selectedCity = city.cityName
        selectedDistrict = null
        selectedDong = null

        val filteredDistricts = allLocations.filter { it.cityName == city.cityName }
            .distinctBy { it.districtName }

        val districtAdapter = DistrictRVAdapter(filteredDistricts) { location ->
            onDistrictItemClick(location, allLocations)
        }
        binding.searchLocDistrictRv.adapter = districtAdapter
        binding.searchLocDistrictRv.visibility = View.VISIBLE
        binding.searchLocDongRv.visibility = View.GONE // 동 RecyclerView 숨기기
    }

    private fun onDistrictItemClick(location: Location, allLocations: List<Location>) {
        selectedDistrict = location.districtName
        selectedDong = null

        val filteredDongLocations = allLocations.filter { it.districtName == location.districtName }

        val dongAdapter = DongRVAdapter(filteredDongLocations) { dongLocation ->
            onDongItemClick(dongLocation)
        }
        binding.searchLocDongRv.adapter = dongAdapter
        binding.searchLocDongRv.visibility = View.VISIBLE
    }

    private fun onDongItemClick(location: Location) {
        selectedDong = location.dongName
        val completeAddress = "$selectedCity $selectedDistrict $selectedDong"

        // 데이터를 전달하기 위해 FragmentResult 설정
        val result = Bundle().apply {
            putString("requestAddress", completeAddress)
        }
        parentFragmentManager.setFragmentResult("requestKey", result)

        // 이전 프래그먼트로 돌아가기
        parentFragmentManager.popBackStack()
    }

    private fun generateDummyItems(): List<Location> {
        val csvFileName = "location.csv"
        val items = CSVUtils.readLocations(requireContext(), csvFileName)

        return items
    }

    private fun generateDummyItem(): List<City> {
        val item = ArrayList<City>()
        item.add(City("강원특별자치도"))
        item.add(City("경기도"))
        item.add(City("경상남도"))
        item.add(City("경상북도"))
        item.add(City("광주광역시"))
        item.add(City("대구광역시"))
        item.add(City("대전광역시"))
        item.add(City("부산광역시"))
        item.add(City("서울특별시"))
        item.add(City("세종특별자치시"))
        item.add(City("울산광역시"))
        item.add(City("인천광역시"))
        item.add(City("전라남도"))
        item.add(City("전북특별자치도"))
        item.add(City("제주특별자치도"))
        item.add(City("충청남도"))
        item.add(City("충청북도"))

        return item
    }
}