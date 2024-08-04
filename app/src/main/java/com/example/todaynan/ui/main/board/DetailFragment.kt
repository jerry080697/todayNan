package com.example.todaynan.ui.main.board

import android.os.Bundle
import android.view.KeyEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.R
import com.example.todaynan.data.entity.Post
import com.example.todaynan.databinding.FragmentDetailBinding
import com.example.todaynan.ui.BaseFragment
import com.example.todaynan.ui.adapter.PostRVAdapter

class DetailFragment: BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    companion object {
        fun newInstance(text: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString("type", text)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initAfterBinding() {

        val type = arguments?.getString("type")
        binding.detailTv.text = type

        val items = generateDummyItems() // 데이터 생성 (임시 함수)
        val boardAdapter = PostRVAdapter(items)
        binding.detailBoardRv.adapter = boardAdapter
        binding.detailBoardRv.layoutManager = LinearLayoutManager(context)
        boardAdapter.setMyItemClickListner(object : PostRVAdapter.MyItemClickListner{
            override fun onItemClick(post: Post) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, PostFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }

        })

        binding.searchImageBt1.setOnClickListener {
            hideKeyboard()
            // 검색 결과 서버 요청
        }
        binding.resultEt.setOnEditorActionListener { v, actionId, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard()
                binding.resultEt.text = binding.resultEt.text
                // 검색 결과 서버 요청
                true // 이벤트 처리 완료
            } else {
                false // 이벤트 처리 안 함
            }
        }

        binding.detailRegisterCl.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, RegisterFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

    private fun generateDummyItems(): List<Post> {
        val items = ArrayList<Post>()
        items.add(Post("띠드버거", R.drawable.default_profile_img,"05.06 14:30","마포구 상암동","잠실 진저베어 신상","안녕하떼여 띠드버거임니당!\n이번 주말에 딩딩이랑 잠실에 갔는데요,,,","추천 게시판",21,15))
        items.add(Post("재밌으면 짖는 개", R.drawable.default_profile_img,"05.04 14:20","광명시 철산동","성심당","없어지면 내가 가게 차린다ㅋㅋ\n마라탕후루집으로 차릴거임","잡담 게시판",33,50))
        items.add(Post("AI입니다", R.drawable.default_profile_img,"05.02 11:30","구로구 구로동","현대미술관 띱","저랑 같이 보러 가실 분?\n밥 사드림","구인 게시판",15,10))
        items.add(Post("띠드버거", R.drawable.default_profile_img,"05.06 14:30","마포구 상암동","잠실 진저베어 신상","안녕하떼여 띠드버거임니당!\n이번 주말에 딩딩이랑 잠실에 갔는데요,,,","추천 게시판",21,15))
        items.add(Post("재밌으면 짖는 개", R.drawable.default_profile_img,"05.04 14:20","광명시 철산동","성심당","없어지면 내가 가게 차린다ㅋㅋ\n마라탕후루집으로 차릴거임","잡담 게시판",33,50))
        items.add(Post("AI입니다", R.drawable.default_profile_img,"05.02 11:30","구로구 구로동","현대미술관 띱","저랑 같이 보러 가실 분?\n밥 사드림","구인 게시판",15,10))
        items.add(Post("띠드버거", R.drawable.default_profile_img,"05.06 14:30","마포구 상암동","잠실 진저베어 신상","안녕하떼여 띠드버거임니당!\n이번 주말에 딩딩이랑 잠실에 갔는데요,,,","추천 게시판",21,15))
        items.add(Post("재밌으면 짖는 개", R.drawable.default_profile_img,"05.04 14:20","광명시 철산동","성심당","없어지면 내가 가게 차린다ㅋㅋ\n마라탕후루집으로 차릴거임","잡담 게시판",33,50))
        items.add(Post("AI입니다", R.drawable.default_profile_img,"05.02 11:30","구로구 구로동","현대미술관 띱","저랑 같이 보러 가실 분?\n밥 사드림","구인 게시판",15,10))
        return items
    }
}