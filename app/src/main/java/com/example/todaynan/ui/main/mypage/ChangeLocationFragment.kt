package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.databinding.FragmentBoardBinding
import com.example.todaynan.databinding.FragmentChangeLocationBinding
import com.example.todaynan.ui.BaseFragment

class ChangeLocationFragment : BaseFragment<FragmentChangeLocationBinding>(FragmentChangeLocationBinding::inflate) {

    override fun initAfterBinding() {

        binding.changeLocationBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.changeLocationCv.setOnClickListener {
            //signup_page3.xml화면이 뜨도록 수정
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ChangeNewLocationFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.changeLocationCurrentLocationTv.setText(AppData.address)
        binding.changeLocationChangeBtnIv.setOnClickListener {
            Toast.makeText(context, "내 동네가 변경되었습니다.", Toast.LENGTH_SHORT).show()
        }

    }
}
