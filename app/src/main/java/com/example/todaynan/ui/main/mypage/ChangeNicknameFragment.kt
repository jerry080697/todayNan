package com.example.todaynan.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todaynan.databinding.FragmentBoardBinding
import com.example.todaynan.databinding.FragmentChangeNicknameBinding
import com.example.todaynan.ui.BaseFragment

class ChangeNicknameFragment : BaseFragment<FragmentChangeNicknameBinding>(FragmentChangeNicknameBinding::inflate) {

    private lateinit var nickNameEt: EditText
    private lateinit var newNickNameTv:TextView
    private lateinit var changeBtn:ImageView
    override fun initAfterBinding() {

        binding.changeNicknameBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        nickNameEt = binding.changeNicknameNewNicknameEt
        newNickNameTv = binding.changeNicknameCurrentNicknameTv
        changeBtn=binding.changeNicknameChangeBtnIv
        changeBtn.setOnClickListener {
            val newNickname = nickNameEt.text.toString()
            newNickNameTv.text = newNickname
            Toast.makeText(context, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
