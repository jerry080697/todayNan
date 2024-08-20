package com.example.todaynan.ui.main.search

import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.place.GeminiItem
import com.example.todaynan.data.remote.place.GoogleItem
import com.example.todaynan.data.remote.place.Outside
import com.example.todaynan.data.remote.place.PlaceInterface
import com.example.todaynan.data.remote.place.PlaceResponse
import com.example.todaynan.databinding.FragmentResultBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.InsideRVAdapter
import com.example.todaynan.ui.adapter.OutsideRVAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultFragment : BaseFragment<FragmentResultBinding>(FragmentResultBinding::inflate) {

    var showType: Int = 0   //0: 크게 보기, 1: 작게 보기
    lateinit var screenAddress: String
    private var insideItemList : ArrayList<GeminiItem>? = null
    private var outsideItemList: ArrayList<GoogleItem>? = null

    override fun initAfterBinding() {

        screenAddress = AppData.address
        binding.locInfoTv.text = screenAddress.split(" ").last()

        val sharedPreferences = requireActivity().getSharedPreferences("showType", AppCompatActivity.MODE_PRIVATE)
        showType = sharedPreferences.getString("showing", "0")?.toInt() ?: 0
        Log.d("TAG_show", showType.toString())
        if (showType == 0){
            binding.resultListRv.isVisible = true
            binding.resultBlockRv.isVisible = false
            binding.resultMenuIv.setImageResource(R.drawable.search_large_off)
        } else {
            binding.resultListRv.isVisible = false
            binding.resultBlockRv.isVisible = true
            binding.resultMenuIv.setImageResource(R.drawable.search_small_off)
        }

        val word = arguments?.getString("keyword")
        binding.resultEt.setText(word)

        chooseType()
        changeScreen()
        changeLoc()

        val place = arguments?.getString("place")
        if(place == "inside"){
            // 상단바 디자인
            binding.searchBar1.visibility = View.INVISIBLE
            binding.searchHomeIcIv.visibility = View.VISIBLE
            // 안 결과 데이터
            arguments?.let {
                insideItemList = it.getSerializable("insideItem") as? ArrayList<GeminiItem>
            }
            val insideRVAdapter1 = InsideRVAdapter(insideItemList,1)
            binding.resultListRv.adapter = insideRVAdapter1
            binding.resultListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val insideRVAdapter2 = InsideRVAdapter(insideItemList,2)
            binding.resultBlockRv.adapter = insideRVAdapter2
            binding.resultBlockRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        } else{ // place == "outside"
            // 상단바 디자인
            binding.searchBar1.visibility = View.VISIBLE
            binding.searchHomeIcIv.visibility = View.INVISIBLE
            // 밖 결과 데이터
            arguments?.let {
                outsideItemList = it.getSerializable("outsideItem") as? ArrayList<GoogleItem>
            }
            val outsideRVAdapter1 = OutsideRVAdapter(outsideItemList,1)
            binding.resultListRv.adapter = outsideRVAdapter1
            binding.resultListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val outsideRVAdapter2 = OutsideRVAdapter(outsideItemList,2)
            binding.resultBlockRv.adapter = outsideRVAdapter2
            binding.resultBlockRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    private fun changeLoc(){
        binding.resultLocChangeIv.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, SearchLocationFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()

            parentFragmentManager.setFragmentResultListener("requestKey", this) { key, bundle ->
                // 데이터를 수신하고 처리합니다.
                val requestAddress = bundle.getString("requestAddress")
                screenAddress = requestAddress.toString()
                binding.locInfoTv.text = screenAddress.split(" ").last()

                outsideResult(binding.resultEt.text.toString())
            }
        }
    }

    private fun changeScreen(){
        binding.searchImageBt1.setOnClickListener {
            hideKeyboard()
            binding.resultEt.text = binding.resultEt.text
            // 검색 결과 갱신
            outsideResult(binding.resultEt.text.toString())
        }
        binding.resultEt.setOnEditorActionListener { v, actionId, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard()
                binding.resultEt.text = binding.resultEt.text
                // 검색 결과 갱신
                outsideResult(binding.resultEt.text.toString())
                true // 이벤트 처리 완료
            } else {
                false // 이벤트 처리 안 함
            }
        }

        binding.searchBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    private fun outsideResult(keyword: String){
        val placeService = getRetrofit().create(PlaceInterface::class.java)

        val auth = "Bearer "+AppData.appToken
        val searchWord = "$screenAddress $keyword"
        Log.d("TAG_outside", searchWord)
        placeService.searchOutside(auth, searchWord, null).enqueue(object:
            Callback<PlaceResponse<Outside>> {
            override fun onResponse(
                call: Call<PlaceResponse<Outside>>,
                response: Response<PlaceResponse<Outside>>
            ) {
                Log.d("TAG_outsideSuccess", response.toString())
                Log.d("TAG_outsideSuccess", response.body().toString())
                val resp = response.body()
                if(resp?.isSuccess == true && resp?.code == "OK200"){
                    outsideItemList = resp.result.googlePlaceResultDTOList

                    // 새로운 데이터로 업데이트
                    (binding.resultListRv.adapter as OutsideRVAdapter).updateData(outsideItemList)
                    (binding.resultBlockRv.adapter as OutsideRVAdapter).updateData(outsideItemList)
                }else{
                }
            }

            override fun onFailure(call: Call<PlaceResponse<Outside>>, t: Throwable) {
                Log.d("TAG_outsideFail", t.message.toString())
            }

        })
    }


    private fun chooseType(){
        val sharedPreferences = requireContext().getSharedPreferences("showType", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.resultMenuIv.setOnClickListener{
            if(showType == 0)
                binding.resultMenuIv.setImageResource(R.drawable.search_large_on)
            else
                binding.resultMenuIv.setImageResource(R.drawable.search_small_on)

            val popupView = layoutInflater.inflate(R.layout.popup_result, null)
            val popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            // 팝업의 외곽을 클릭하면 닫히도록 설정
            popupWindow.isOutsideTouchable = true
            popupWindow.isFocusable = true

            // 팝업의 위치를 설정하여 화면에 표시
            val anchorView = binding.resultMenuIv // 기준 뷰
            popupWindow.showAsDropDown(anchorView, -270, 0)

            // 팝업 아이템 클릭 리스너 설정
            popupView.findViewById<ConstraintLayout>(R.id.menu_large_iv).setOnClickListener {
                outsideResult(binding.resultEt.text.toString())
                binding.resultListRv.isVisible = true
                binding.resultBlockRv.isVisible = false
                showType = 0
                editor.putString("showing", showType.toString())
                editor.apply()
                popupWindow.dismiss()
            }
            popupView.findViewById<ConstraintLayout>(R.id.menu_small_iv).setOnClickListener {
                outsideResult(binding.resultEt.text.toString())
                binding.resultListRv.isVisible = false
                binding.resultBlockRv.isVisible = true
                showType = 1
                editor.putString("showing", showType.toString())
                editor.apply()
                popupWindow.dismiss()
            }

            popupWindow.setOnDismissListener {
                if(showType == 0)
                    binding.resultMenuIv.setImageResource(R.drawable.search_large_off)
                else
                    binding.resultMenuIv.setImageResource(R.drawable.search_small_off)
            }
        }
    }

}