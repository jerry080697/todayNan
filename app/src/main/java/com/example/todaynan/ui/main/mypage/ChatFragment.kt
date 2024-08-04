package com.example.todaynan.ui.main.mypage

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.ui.adapter.ChatRVAdapter
import com.example.todaynan.R
import com.example.todaynan.data.entity.Chat
import com.example.todaynan.databinding.FragmentChatBinding
import com.example.todaynan.ui.BaseFragment

class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    override fun initAfterBinding() {

        val items = generateDummyItems() // 임시 더미 데이터
        val postAdapter = ChatRVAdapter(items)
        binding.postListRv.adapter = postAdapter
        binding.postListRv.layoutManager = LinearLayoutManager(context)


        binding.postBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }
    private fun generateDummyItems(): List<Chat> {
        val items = ArrayList<Chat>()
        items.add(Chat("전주비빔", R.drawable.default_profile_img,"언제쯤 도착하시나요?","05.06 14:30",false))
        items.add(Chat("피까삐까", R.drawable.default_profile_img,"인사이드 아웃2 스포해도 돼요??","05.06 14:30",false))
        items.add(Chat("바라바라밤", R.drawable.default_profile_img,"수수수수퍼노바","05.06 14:30",true))
        items.add(Chat("배고파배고파", R.drawable.default_profile_img,"마라탕 사주세여","05.06 14:30",true))
        items.add(Chat("전주통닭", R.drawable.default_profile_img,"으어어어졸려","05.06 14:30",true))
        items.add(Chat("날짜는", R.drawable.default_profile_img,"안바꿀래여 흥","05.06 14:30",true))

        return items
    }

}
