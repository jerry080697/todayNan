package com.example.todaynan.ui.signup

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
<<<<<<< HEAD
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.data.entity.City
import com.example.todaynan.data.entity.Location
import com.example.todaynan.databinding.SignupPage3Binding
import com.example.todaynan.ui.adapter.CityRVAdapter
import com.example.todaynan.ui.adapter.DistrictRVAdapter
import com.example.todaynan.ui.adapter.DongRVAdapter

class Page3SignUpActivity : AppCompatActivity() {

    lateinit var binding: SignupPage3Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupPage3Binding.inflate(layoutInflater)
        setContentView(binding.root)
=======
import com.example.todaynan.databinding.SignupPage1Binding
import com.example.todaynan.databinding.SignupPage3Binding
import com.example.todaynan.ui.BaseActivity

class Page3SignUpActivity : BaseActivity<SignupPage3Binding>(SignupPage3Binding::inflate) {
    override fun initAfterBinding() {
>>>>>>> 7acdb94dba7e38592d38688a468c644ee21a2b10

        val allLocations = generateDummyItems()
        val cities = generateDummyItem()

        val cityAdapter = CityRVAdapter(cities) { city ->
            onCityItemClick(city, allLocations)
        }
        binding.signupPage3CityRv.adapter = cityAdapter
        binding.signupPage3CityRv.layoutManager = LinearLayoutManager(this)

        val districtAdapter = DistrictRVAdapter(emptyList()) { location ->
            onDistrictItemClick(location, allLocations)
        }
        binding.signupPage3DistrictRv.adapter = districtAdapter
        binding.signupPage3DistrictRv.layoutManager = LinearLayoutManager(this)

        val dongAdapter = DongRVAdapter(emptyList())
        binding.signupPage3DongRv.adapter = dongAdapter
        binding.signupPage3DongRv.layoutManager = LinearLayoutManager(this)

        binding.signupPage3Back.setOnClickListener {
            finish()
        }
    }
    private fun onCityItemClick(city: City, allLocations: List<Location>) {
        // 클릭된 도시를 기준으로 Location 데이터 필터링
        val filteredDistricts = allLocations.filter { it.cityName == city.cityName }
            .distinctBy { it.districtName } // 중복 구역 제거

        // DistrictRVAdapter 업데이트
        val districtAdapter = DistrictRVAdapter(filteredDistricts) { location ->
            onDistrictItemClick(location, allLocations)
        }
        binding.signupPage3DistrictRv.adapter = districtAdapter
        binding.signupPage3DistrictRv.visibility = View.VISIBLE
        binding.signupPage3DongRv.visibility = View.GONE // 초기에는 동네 리스트를 숨김
    }

    private fun onDistrictItemClick(location: Location, allLocations: List<Location>) {
        // 클릭된 구역을 기준으로 Location 데이터 필터링
        val filteredDongLocations = allLocations.filter { it.districtName == location.districtName }

        // DongRVAdapter 업데이트
        val dongAdapter = DongRVAdapter(filteredDongLocations)
        binding.signupPage3DongRv.adapter = dongAdapter
        binding.signupPage3DongRv.visibility = View.VISIBLE
    }

    private fun generateDummyItems(): List<Location> {
        val items = ArrayList<Location>()
        items.add(Location("서울특별시","관악구","낙성대동"))
        items.add(Location("서울특별시","관악구","난공동"))
        items.add(Location("서울특별시","관악구","난항동"))
        items.add(Location("서울특별시","관악구","남현동"))
        items.add(Location("서울특별시","관악구","대학동"))
        items.add(Location("서울특별시","관악구","미성동"))
        items.add(Location("서울특별시","관악구","보라매동"))
        items.add(Location("서울특별시","관악구","봉천동"))
        items.add(Location("서울특별시","관악구","삼성동"))
        items.add(Location("서울특별시","관악구","서림동"))
        items.add(Location("서울특별시","관악구","서원동"))
        items.add(Location("서울특별시","관악구","성현동"))

        items.add(Location("서울특별시","강남구","대치동"))


        items.add(Location("경기도","광명시","철산동"))
        items.add(Location("경기도","광명시","하안동"))
        items.add(Location("경기도","광명시","소하동"))
        items.add(Location("경기도","광명시","광명동"))
        items.add(Location("경기도","광명시","노온사동"))
        items.add(Location("경기도","광명시","학온동"))
        items.add(Location("경기도","광명시","일직동"))

        items.add(Location("경기도","안양시","비산동"))


        items.add(Location("인천광역시","미추홀구","주안동"))

        items.add(Location("부산광역시","해운대구","반여동"))

        items.add(Location("울산광역시","중구","교동"))

        items.add(Location("대구광역시","북구","검단동"))

        items.add(Location("광주광역시","서구","금호동"))

        items.add(Location("대전광역시","동구","가양동"))

        items.add(Location("세종특별자치시","ㅁㅁ구","보람동"))

        items.add(Location("강원도","원주시","우산동"))

        items.add(Location("충청북도","괴산군","괴산읍"))

        items.add(Location("충청남도","보령시","대청동"))

        items.add(Location("전라남도","여수시","동문동"))

        items.add(Location("전라북도","익산시","동산동"))

        items.add(Location("경상남도","김해시","구산동"))

        items.add(Location("경상북도","안동시","광석동"))

        items.add(Location("제주특별자치도","제주시","화북동"))

        return items
    }
    private fun generateDummyItem(): List<City> {
        val item = ArrayList<City>()
        item.add(City("서울특별시"))
        item.add(City("경기도"))
        item.add(City("인천광역시"))
        item.add(City("부산광역시"))
        item.add(City("울산광역시"))
        item.add(City("대구광역시"))
        item.add(City("광주광역시"))
        item.add(City("대전광역시"))
        item.add(City("세종특별자치시"))
        item.add(City("강원도"))
        item.add(City("충청북도"))
        item.add(City("충청남도"))
        item.add(City("전라북도"))
        item.add(City("전라남도"))
        item.add(City("경상북도"))
        item.add(City("경상남도"))
        item.add(City("제주특별자치도"))

        return item
    }
}