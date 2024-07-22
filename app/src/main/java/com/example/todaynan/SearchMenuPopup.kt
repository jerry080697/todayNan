package com.example.todaynan

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.example.todaynan.databinding.PopupResultMenuBinding

data class PopupValue(
    val icon: Int,
    val type: String
)

class SearchMenuPopup(
    context: Context,
    popupList: MutableList<PopupValue>,
    menuItemListener: (View?, PopupValue, Int) -> Unit,
) : PopupWindow(context) {

    init {
        val inflater = LayoutInflater.from(context)

        val binding = PopupResultMenuBinding.inflate(inflater, null, false)
        contentView = binding.root

        width = getDp(context, 110.0f)  //팝업창 가로 길이 설정

        val layoutParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 80)

        //각 메뉴 항목에 대한 뷰 생성
        for (i in 0 until popupList.size) {
            val view = inflater.inflate(R.layout.popup_result_item, null, false)

            val menuIcon = view.findViewById(R.id.menu_item_iv) as ImageView
            val menuType = view.findViewById(R.id.menu_item_tv) as TextView
            menuIcon.setImageResource(popupList[i].icon)
            menuType.text = popupList[i].type

            view.setOnClickListener {
                menuItemListener.invoke(it, popupList[i], i)
                dismiss()   //팝업창 닫기
            }
            binding.popupLl.addView(view, layoutParam)
        }

        //팝업창 세로길이 설정
        val layout = contentView
        layout.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        height = layout.measuredHeight + 55
    }

    //팝업창 너비 설정
    private fun getDp(context: Context, value: Float): Int {
        val dm = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm).toInt()
    }

}