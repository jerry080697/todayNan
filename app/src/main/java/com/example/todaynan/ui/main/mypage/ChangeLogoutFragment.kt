package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentBoardBinding
import com.example.todaynan.databinding.FragmentChangeLogoutBinding
import com.example.todaynan.ui.BaseFragment

class ChangeLogoutFragment : BaseFragment<FragmentChangeLogoutBinding>(FragmentChangeLogoutBinding::inflate) {

    override fun initAfterBinding() {

        binding.changeLogoutBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        showLogoutConfirmationDialog()

    }
    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("로그아웃")
        builder.setMessage("정말 로그아웃 하시겠습니까?")

        // 긍정 버튼
        builder.setPositiveButton("예") { dialog, which ->
            Toast.makeText(context, "로그아웃이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            //로그아웃 로직
        }

        // 부정 버튼
        builder.setNegativeButton("아니요") { dialog, which ->
            dialog.dismiss()
        }

        // 다이얼로그를 표시
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
