package com.example.todaynan.ui.main.mypage

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.data.entity.Post
import com.example.todaynan.R
import com.example.todaynan.base.AppData
import com.example.todaynan.databinding.FragmentMyBoardBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.LikePostRVAdapter

class MyBoardFragment : BaseFragment<FragmentMyBoardBinding>(FragmentMyBoardBinding::inflate) {
    override fun initAfterBinding() {
        binding.boardNicknameTv.text = AppData.nickname
        binding.boardBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

//        binding.likeBoard.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.main_frm, MyBoardLikeFragment())
//                .addToBackStack(null)
//                .commitAllowingStateLoss()
//        }
        binding.replyBoard.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MyBoardReplyFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        binding.writeBoard.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MyBoardWriteFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }
}
