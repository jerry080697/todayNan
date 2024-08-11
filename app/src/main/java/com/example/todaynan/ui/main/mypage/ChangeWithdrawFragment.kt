package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import com.example.todaynan.databinding.FragmentChangeWithdrawBinding
import com.example.todaynan.ui.BaseFragment

class ChangeWithdrawFragment : BaseFragment<FragmentChangeWithdrawBinding>(FragmentChangeWithdrawBinding::inflate) {

    override fun initAfterBinding() {

        binding.changeWithdrawBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        showWithdrawConfirmationDialog()
    }

    private fun showWithdrawConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("회원 탈퇴")
        builder.setMessage("정말 회원탈퇴를 진행하시겠습니까?")

        // 긍정 버튼
        builder.setPositiveButton("예") { dialog, which ->
            Toast.makeText(context, "회원탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
            // 여기서 회원탈퇴 로직을 추가할 수 있습니다.
        }

        // 부정 버튼
        builder.setNegativeButton("아니요") { dialog, which ->
            // 아무 작업도 하지 않고 다이얼로그를 닫습니다.
            dialog.dismiss()
        }

        // 다이얼로그를 보여줍니다.
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
