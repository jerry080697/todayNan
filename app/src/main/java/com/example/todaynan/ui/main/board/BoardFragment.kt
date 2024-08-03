package com.example.todaynan.ui.main.board

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.R
import com.example.todaynan.data.entity.Post
import com.example.todaynan.databinding.FragmentBoardBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.LikePostRVAdapter

class BoardFragment : BaseFragment<FragmentBoardBinding>(FragmentBoardBinding::inflate) {
    override fun initAfterBinding() {
        // 임시 데이터
        val items = ArrayList<Post>()
        items.add(Post("띠드버거", R.drawable.default_profile_img,"05.06 14:30","마포구 상암동","잠실 진저베어 신상","안녕하떼여 띠드버거임니당!\n이번 주말에 딩딩이랑 잠실에 갔는데요,,,","추천 게시판",21,15))
        val boardAdapter = LikePostRVAdapter(items)
        binding.hotPostRv.adapter = boardAdapter
        binding.hotPostRv.layoutManager = LinearLayoutManager(context)

        binding.recruitBoard.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, RecruitFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }
}