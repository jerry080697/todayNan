package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentBoardBinding
import com.example.todaynan.databinding.FragmentChangeLogoutBinding
import com.example.todaynan.ui.BaseFragment

class ChangeLogoutFragment : BaseFragment<FragmentChangeLogoutBinding>(FragmentChangeLogoutBinding::inflate) {

    override fun initAfterBinding() {

        binding.changeLogoutBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.logoutChangeBtnIv.setOnClickListener {
            Toast.makeText(context, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
        }

    }
}
