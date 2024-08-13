package com.example.todaynan.ui.main.mypage

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynan.R
import com.example.todaynan.ui.adapter.RecommendRVAdapter
import com.example.todaynan.data.entity.Recommend
import com.example.todaynan.databinding.FragmentJjimListBinding
import com.example.todaynan.ui.BaseFragment


class JjimListFragment : BaseFragment<FragmentJjimListBinding>(FragmentJjimListBinding::inflate) {

    override fun initAfterBinding() {
        /*
        val items = generateDummyItems() // 데이터 생성 (임시 함수)
        val jjimListAdapter = RecommendRVAdapter(items, 2)
        binding.jjimListRv.adapter = jjimListAdapter
        binding.jjimListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        */
        binding.jjimListBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


    }
}
    private fun generateDummyItems(): ArrayList<Recommend> {
        val items = ArrayList<Recommend>()

        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀1", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀2", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀3", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀4", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀5", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀6", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀7", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀8", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀9", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀10", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀11", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀12", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀13", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀14", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))
        items.add(Recommend("영화", R.drawable.item_temp_img, "시간을 달리는 소녀15", "드라마/로맨스, 2006", "영화설명 가나다라마바사아자차카타파하"))

        return items
    }
