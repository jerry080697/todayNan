package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentChangeWithdrawBinding
import com.example.todaynan.databinding.FragmentSearchBinding
import com.example.todaynan.ui.BaseFragment

class ChangeWithdrawFragment : BaseFragment<FragmentChangeWithdrawBinding>(FragmentChangeWithdrawBinding::inflate) {

    override fun initAfterBinding() {

        binding.changeWithdrawBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.withdrawChangeBtnIv.setOnClickListener {
            Toast.makeText(context, "회원탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
        }

    }
}
