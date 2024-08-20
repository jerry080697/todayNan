package com.example.todaynan.ui.main.search

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.data.remote.getRetrofit
import com.example.todaynan.data.remote.place.Inside
import com.example.todaynan.data.remote.place.Outside
import com.example.todaynan.data.remote.place.PlaceInterface
import com.example.todaynan.data.remote.place.PlaceResponse
import com.example.todaynan.databinding.FragmentSearchBinding
import com.example.todaynan.ui.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override fun initAfterBinding() {

        if(AppData.mypet == "DOG"){
            binding.searchPetFox.visibility = View.VISIBLE
            binding.searchPetBird.visibility = View.GONE
            binding.searchPetBear.visibility = View.GONE
        }else if(AppData.mypet == "CAT"){
            binding.searchPetBird.visibility = View.VISIBLE
            binding.searchPetFox.visibility = View.GONE
            binding.searchPetBear.visibility = View.GONE
        }else if(AppData.mypet == "QUOKKA"){
            binding.searchPetBear.visibility = View.VISIBLE
            binding.searchPetFox.visibility = View.GONE
            binding.searchPetBird.visibility = View.GONE
        }

        changeScreen()
    }

    override fun onResume() {
        super.onResume()

        binding.requestEt.text = null
    }

    private fun changeScreen(){
        binding.searchImageBt0.setOnClickListener {
            hideKeyboard()
            val searchText = binding.requestEt.text.toString()
            outsideResult(searchText)
        }
        binding.requestEt.setOnEditorActionListener { v, actionId, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard()
                val searchText = binding.requestEt.text.toString()
                outsideResult(searchText)
                true // 이벤트 처리 완료
            } else {
                false // 이벤트 처리 안 함
            }
        }
        binding.searchHomeBt.setOnClickListener {
            Toast.makeText(context, "잠시만 기다려주세요", Toast.LENGTH_SHORT).show()
            insideResult()
        }
    }

    private fun insideResult(){
        val placeService = getRetrofit().create(PlaceInterface::class.java)

        val auth = "Bearer "+AppData.appToken
        placeService.searchInside(auth).enqueue(object: Callback<PlaceResponse<Inside>> {
            override fun onResponse(
                call: Call<PlaceResponse<Inside>>,
                response: Response<PlaceResponse<Inside>>
            ) {
                Log.d("TAG_insideSuccess", response.toString())
                Log.d("TAG_insideSuccess", response.body().toString())
                val resp = response.body()
                if(resp?.isSuccess == true && resp?.code == "SEARCH2003"){
                    val bundle = Bundle().apply {
                        putSerializable("insideItem", resp!!.result.geminiResponseItemDTOList)
                        putString("place", "inside")
                    }
                    val resultFragment = ResultFragment()
                    resultFragment.arguments = bundle
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, resultFragment)
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                } else{
                }
            }

            override fun onFailure(call: Call<PlaceResponse<Inside>>, t: Throwable) {
                Log.d("TAG_insideFail", t.message.toString())
            }

        })
    }

    private fun outsideResult(keyword: String){
        val placeService = getRetrofit().create(PlaceInterface::class.java)

        val auth = "Bearer "+AppData.appToken
        val searchWord = AppData.address+" "+keyword
        Log.d("TAG_outside", searchWord)
        placeService.searchOutside(auth, searchWord, null).enqueue(object: Callback<PlaceResponse<Outside>>{
            override fun onResponse(
                call: Call<PlaceResponse<Outside>>,
                response: Response<PlaceResponse<Outside>>
            ) {
                Log.d("TAG_outsideSuccess", response.toString())
                Log.d("TAG_outsideSuccess", response.body().toString())
                val resp = response.body()
                if(resp?.isSuccess == true && resp?.code == "OK200"){
                    val bundle = Bundle().apply {
                        putSerializable("outsideItem", resp!!.result.googlePlaceResultDTOList)
                        putString("place", "outside")
                        putString("keyword", keyword)
                    }
                    val resultFragment = ResultFragment()
                    resultFragment.arguments = bundle
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, resultFragment)
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                } else{
                }
            }

            override fun onFailure(call: Call<PlaceResponse<Outside>>, t: Throwable) {
                Log.d("TAG_outsideFail", t.message.toString())
            }

        })
    }

}