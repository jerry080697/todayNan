package com.example.todaynan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todaynan.databinding.ActivityJjimListBinding

class JjimListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJjimListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJjimListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val recyclerView: RecyclerView = findViewById(R.id.jjim_list_rv)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val items = generateDummyItems() // 데이터 생성 (임시 함수)
        val adapter = JjimListRVAdapter(items)
        recyclerView.adapter = adapter
        binding.jjimListBackBtn.setOnClickListener {
            finish()
        }

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.recycler_view_item_spacing)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels, true))
    }
    private fun generateDummyItems(): List<Item> {
        val items = ArrayList<Item>()
        items.add(Item("장소 1", R.drawable.default_img))
        items.add(Item("장소 2", R.drawable.default_img))
        items.add(Item("장소 3", R.drawable.default_img))
        items.add(Item("장소 4", R.drawable.default_img))
        items.add(Item("장소 5", R.drawable.default_img))
        items.add(Item("장소 6", R.drawable.default_img))
        items.add(Item("장소 7", R.drawable.default_img))
        items.add(Item("장소 8", R.drawable.default_img))
        items.add(Item("장소 9", R.drawable.default_img))
        items.add(Item("장소 10", R.drawable.default_img))
        items.add(Item("장소 11", R.drawable.default_img))
        items.add(Item("장소 12", R.drawable.default_img))

        items.add(Item("장소 13", R.drawable.default_img))
        items.add(Item("장소 14", R.drawable.default_img))
        items.add(Item("장소 15", R.drawable.default_img))
        items.add(Item("장소 16", R.drawable.default_img))
        items.add(Item("장소 17", R.drawable.default_img))
        items.add(Item("장소 18", R.drawable.default_img))
        items.add(Item("장소 19", R.drawable.default_img))
        items.add(Item("장소 20", R.drawable.default_img))
        items.add(Item("장소 21", R.drawable.default_img))
        items.add(Item("장소 22", R.drawable.default_img))
        items.add(Item("장소 23", R.drawable.default_img))
        items.add(Item("장소 24", R.drawable.default_img))
        return items
    }
}
