
package com.example.todaynan.ui.signup

import android.view.View
import android.widget.Toast
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.SignupData
import com.example.todaynan.databinding.FragmentChangeInterestBinding
import com.example.todaynan.ui.BaseFragment

class ChangeInterestFragment : BaseFragment<FragmentChangeInterestBinding>(FragmentChangeInterestBinding::inflate) {

    private val selectedOptions = mutableListOf<String>()
    private val selectedOptionsIndex = mutableListOf<Int>()

    override fun initAfterBinding() {
        val strMessage = "preferStr: ${AppData.preferStr.joinToString(", ")}"
        val idxMessage = "preferIdx: ${AppData.preferIdx.joinToString(", ")}"
        Toast.makeText(context, "$strMessage\n$idxMessage", Toast.LENGTH_LONG).show()


        binding.changeInterestBackBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Initialize options
        option1()
        option2()
        option3()
        option4()
        option5()
        option6()
        option7()
        option8()
        option9()
        option10()
        option11()
        option12()
        option13()
        option14()
        option15()
        option16()
        option17()
        option18()
        option19()
        option20()
        option21()

        showSelectedPreferences()

        binding.changeInterestNextBtnBefDark.setOnClickListener {
            if (selectedOptions.size in 1..5) {
                //SignupData.selectedOptions.clear()
                SignupData.selectedOptions.add(AppData.preferIdx.toString())
                SignupData.selectedOptions.addAll(selectedOptions)
                AppData.preferStr = selectedOptions as ArrayList<String>
                AppData.preferIdx = selectedOptionsIndex.map { it.toLong() }

                Toast.makeText(context, "내 관심사 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSelectedPreferences() {
        // Hide all Dark views initially
        hideAllDarkViews()

        // Set Dark visibility based on AppData.preferIdx
        AppData.preferIdx.forEach { idx ->
            when (idx.toInt()) {
                0 -> binding.changeInterestSelect1Dark.visibility = View.VISIBLE
                1 -> binding.changeInterestSelect2Dark.visibility = View.VISIBLE
                2 -> binding.changeInterestSelect3Dark.visibility = View.VISIBLE
                3 -> binding.changeInterestSelect4Dark.visibility = View.VISIBLE
                4 -> binding.changeInterestSelect5Dark.visibility = View.VISIBLE
                5 -> binding.changeInterestSelect6Dark.visibility = View.VISIBLE
                6 -> binding.changeInterestSelect7Dark.visibility = View.VISIBLE
                7 -> binding.changeInterestSelect8Dark.visibility = View.VISIBLE
                8 -> binding.changeInterestSelect9Dark.visibility = View.VISIBLE
                9 -> binding.changeInterestSelect10Dark.visibility = View.VISIBLE
                10 -> binding.changeInterestSelect11Dark.visibility = View.VISIBLE
                11 -> binding.changeInterestSelect12Dark.visibility = View.VISIBLE
                12 -> binding.changeInterestSelect13Dark.visibility = View.VISIBLE
                13 -> binding.changeInterestSelect14Dark.visibility = View.VISIBLE
                14 -> binding.changeInterestSelect15Dark.visibility = View.VISIBLE
                15 -> binding.changeInterestSelect16Dark.visibility = View.VISIBLE
                16 -> binding.changeInterestSelect17Dark.visibility = View.VISIBLE
                17 -> binding.changeInterestSelect18Dark.visibility = View.VISIBLE
                18 -> binding.changeInterestSelect19Dark.visibility = View.VISIBLE
                19 -> binding.changeInterestSelect20Dark.visibility = View.VISIBLE
                20 -> binding.changeInterestSelect21Dark.visibility = View.VISIBLE
            }
        }
    }

    private fun hideAllDarkViews() {
        binding.changeInterestSelect1Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect2Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect3Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect4Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect5Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect6Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect7Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect8Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect9Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect10Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect11Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect12Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect13Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect14Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect15Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect16Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect17Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect18Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect19Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect20Dark.visibility = View.INVISIBLE
        binding.changeInterestSelect21Dark.visibility = View.INVISIBLE
    }

    // The methods for option selection
    fun selectOption1(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect1Dark.visibility = View.VISIBLE
                binding.changeInterestSelect1.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect1Tv.text.toString())
                selectedOptionsIndex.add(0)
            }
        } else {
            binding.changeInterestSelect1Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect1.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect1Tv.text.toString())
            selectedOptionsIndex.remove(0)
        }
    }

    fun selectOption2(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect2Dark.visibility = View.VISIBLE
                binding.changeInterestSelect2.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect2Tv.text.toString())
                selectedOptionsIndex.add(1)
            }
        } else {
            binding.changeInterestSelect2Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect2.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect2Tv.text.toString())
            selectedOptionsIndex.remove(1)
        }
    }

    fun selectOption3(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect3Dark.visibility = View.VISIBLE
                binding.changeInterestSelect3.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect3Tv.text.toString())
                selectedOptionsIndex.add(2)
            }
        } else {
            binding.changeInterestSelect3Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect3.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect3Tv.text.toString())
            selectedOptionsIndex.remove(2)
        }
    }

    fun selectOption4(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect4Dark.visibility = View.VISIBLE
                binding.changeInterestSelect4.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect4Tv.text.toString())
                selectedOptionsIndex.add(3)
            }
        } else {
            binding.changeInterestSelect4Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect4.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect4Tv.text.toString())
            selectedOptionsIndex.remove(3)
        }
    }

    fun selectOption5(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect5Dark.visibility = View.VISIBLE
                binding.changeInterestSelect5.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect5Tv.text.toString())
                selectedOptionsIndex.add(4)
            }
        } else {
            binding.changeInterestSelect5Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect5.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect5Tv.text.toString())
            selectedOptionsIndex.remove(4)
        }
    }

    fun selectOption6(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect6Dark.visibility = View.VISIBLE
                binding.changeInterestSelect6.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect6Tv.text.toString())
                selectedOptionsIndex.add(5)
            }
        } else {
            binding.changeInterestSelect6Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect6.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect6Tv.text.toString())
            selectedOptionsIndex.remove(5)
        }
    }

    fun selectOption7(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect7Dark.visibility = View.VISIBLE
                binding.changeInterestSelect7.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect7Tv.text.toString())
                selectedOptionsIndex.add(6)
            }
        } else {
            binding.changeInterestSelect7Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect7.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect7Tv.text.toString())
            selectedOptionsIndex.remove(6)
        }
    }

    fun selectOption8(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect8Dark.visibility = View.VISIBLE
                binding.changeInterestSelect8.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect8Tv.text.toString())
                selectedOptionsIndex.add(7)
            }
        } else {
            binding.changeInterestSelect8Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect8.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect8Tv.text.toString())
            selectedOptionsIndex.remove(7)
        }
    }

    fun selectOption9(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect9Dark.visibility = View.VISIBLE
                binding.changeInterestSelect9.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect9Tv.text.toString())
                selectedOptionsIndex.add(8)
            }
        } else {
            binding.changeInterestSelect9Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect9.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect9Tv.text.toString())
            selectedOptionsIndex.remove(8)
        }
    }

    fun selectOption10(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect10Dark.visibility = View.VISIBLE
                binding.changeInterestSelect10.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect10Tv.text.toString())
                selectedOptionsIndex.add(9)
            }
        } else {
            binding.changeInterestSelect10Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect10.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect10Tv.text.toString())
            selectedOptionsIndex.remove(9)
        }
    }

    fun selectOption11(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect11Dark.visibility = View.VISIBLE
                binding.changeInterestSelect11.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect11Tv.text.toString())
                selectedOptionsIndex.add(10)
            }
        } else {
            binding.changeInterestSelect11Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect11.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect11Tv.text.toString())
            selectedOptionsIndex.remove(10)
        }
    }

    fun selectOption12(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect12Dark.visibility = View.VISIBLE
                binding.changeInterestSelect12.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect12Tv.text.toString())
                selectedOptionsIndex.add(11)
            }
        } else {
            binding.changeInterestSelect12Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect12.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect12Tv.text.toString())
            selectedOptionsIndex.remove(11)
        }
    }

    fun selectOption13(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect13Dark.visibility = View.VISIBLE
                binding.changeInterestSelect13.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect13Tv.text.toString())
                selectedOptionsIndex.add(12)
            }
        } else {
            binding.changeInterestSelect13Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect13.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect13Tv.text.toString())
            selectedOptionsIndex.remove(12)
        }
    }

    fun selectOption14(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect14Dark.visibility = View.VISIBLE
                binding.changeInterestSelect14.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect14Tv.text.toString())
                selectedOptionsIndex.add(13)
            }
        } else {
            binding.changeInterestSelect14Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect14.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect14Tv.text.toString())
            selectedOptionsIndex.remove(13)
        }
    }

    fun selectOption15(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect15Dark.visibility = View.VISIBLE
                binding.changeInterestSelect15.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect15Tv.text.toString())
                selectedOptionsIndex.add(14)
            }
        } else {
            binding.changeInterestSelect15Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect15.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect15Tv.text.toString())
            selectedOptionsIndex.remove(14)
        }
    }

    fun selectOption16(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect16Dark.visibility = View.VISIBLE
                binding.changeInterestSelect16.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect16Tv.text.toString())
                selectedOptionsIndex.add(15)
            }
        } else {
            binding.changeInterestSelect16Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect16.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect16Tv.text.toString())
            selectedOptionsIndex.remove(15)
        }
    }

    fun selectOption17(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect17Dark.visibility = View.VISIBLE
                binding.changeInterestSelect17.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect17Tv.text.toString())
                selectedOptionsIndex.add(16)
            }
        } else {
            binding.changeInterestSelect17Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect17.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect17Tv.text.toString())
            selectedOptionsIndex.remove(16)
        }
    }

    fun selectOption18(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect18Dark.visibility = View.VISIBLE
                binding.changeInterestSelect18.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect18Tv.text.toString())
                selectedOptionsIndex.add(17)
            }
        } else {
            binding.changeInterestSelect18Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect18.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect18Tv.text.toString())
            selectedOptionsIndex.remove(17)
        }
    }

    fun selectOption19(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect19Dark.visibility = View.VISIBLE
                binding.changeInterestSelect19.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect19Tv.text.toString())
                selectedOptionsIndex.add(18)
            }
        } else {
            binding.changeInterestSelect19Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect19.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect19Tv.text.toString())
            selectedOptionsIndex.remove(18)
        }
    }

    fun selectOption20(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect20Dark.visibility = View.VISIBLE
                binding.changeInterestSelect20.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect20Tv.text.toString())
                selectedOptionsIndex.add(19)
            }
        } else {
            binding.changeInterestSelect20Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect20.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect20Tv.text.toString())
            selectedOptionsIndex.remove(19)
        }
    }

    fun selectOption21(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.changeInterestSelect21Dark.visibility = View.VISIBLE
                binding.changeInterestSelect21.visibility = View.INVISIBLE
                selectedOptions.add(binding.changeInterestSelect21Tv.text.toString())
                selectedOptionsIndex.add(20)
            }
        } else {
            binding.changeInterestSelect21Dark.visibility = View.INVISIBLE
            binding.changeInterestSelect21.visibility = View.VISIBLE
            selectedOptions.remove(binding.changeInterestSelect21Tv.text.toString())
            selectedOptionsIndex.remove(20)
        }
    }
        fun option1() {
        binding.changeInterestSelect1.setOnClickListener {
            selectOption1(true)
            checkToNext()
        }
        binding.changeInterestSelect1Dark.setOnClickListener {
            selectOption1(false)
            checkToNext()
        }
    }

    fun option2() {
        binding.changeInterestSelect2.setOnClickListener {
            selectOption2(true)
            checkToNext()
        }
        binding.changeInterestSelect2Dark.setOnClickListener {
            selectOption2(false)
            checkToNext()
        }
    }

    fun option3() {
        binding.changeInterestSelect3.setOnClickListener {
            selectOption3(true)
            checkToNext()
        }
        binding.changeInterestSelect3Dark.setOnClickListener {
            selectOption3(false)
            checkToNext()
        }
    }

    fun option4() {
        binding.changeInterestSelect4.setOnClickListener {
            selectOption4(true)
            checkToNext()
        }
        binding.changeInterestSelect4Dark.setOnClickListener {
            selectOption4(false)
            checkToNext()
        }
    }

    fun option5() {
        binding.changeInterestSelect5.setOnClickListener {
            selectOption5(true)
            checkToNext()
        }
        binding.changeInterestSelect5Dark.setOnClickListener {
            selectOption5(false)
            checkToNext()
        }
    }

    fun option6() {
        binding.changeInterestSelect6.setOnClickListener {
            selectOption6(true)
            checkToNext()
        }
        binding.changeInterestSelect6Dark.setOnClickListener {
            selectOption6(false)
            checkToNext()
        }
    }

    fun option7() {
        binding.changeInterestSelect7.setOnClickListener {
            selectOption7(true)
            checkToNext()
        }
        binding.changeInterestSelect7Dark.setOnClickListener {
            selectOption7(false)
            checkToNext()
        }
    }

    fun option8() {
        binding.changeInterestSelect8.setOnClickListener {
            selectOption8(true)
            checkToNext()
        }
        binding.changeInterestSelect8Dark.setOnClickListener {
            selectOption8(false)
            checkToNext()
        }
    }

    fun option9() {
        binding.changeInterestSelect9.setOnClickListener {
            selectOption9(true)
            checkToNext()
        }
        binding.changeInterestSelect9Dark.setOnClickListener {
            selectOption9(false)
            checkToNext()
        }
    }

    fun option10() {
        binding.changeInterestSelect10.setOnClickListener {
            selectOption10(true)
            checkToNext()
        }
        binding.changeInterestSelect10Dark.setOnClickListener {
            selectOption10(false)
            checkToNext()
        }
    }

    fun option11() {
        binding.changeInterestSelect11.setOnClickListener {
            selectOption11(true)
            checkToNext()
        }
        binding.changeInterestSelect11Dark.setOnClickListener {
            selectOption11(false)
            checkToNext()
        }
    }

    fun option12() {
        binding.changeInterestSelect12.setOnClickListener {
            selectOption12(true)
            checkToNext()
        }
        binding.changeInterestSelect12Dark.setOnClickListener {
            selectOption12(false)
            checkToNext()
        }
    }

    fun option13() {
        binding.changeInterestSelect13.setOnClickListener {
            selectOption13(true)
            checkToNext()
        }
        binding.changeInterestSelect13Dark.setOnClickListener {
            selectOption13(false)
            checkToNext()
        }
    }

    fun option14() {
        binding.changeInterestSelect14.setOnClickListener {
            selectOption14(true)
            checkToNext()
        }
        binding.changeInterestSelect14Dark.setOnClickListener {
            selectOption14(false)
            checkToNext()
        }
    }

    fun option15() {
        binding.changeInterestSelect15.setOnClickListener {
            selectOption15(true)
            checkToNext()
        }
        binding.changeInterestSelect15Dark.setOnClickListener {
            selectOption15(false)
            checkToNext()
        }
    }

    fun option16() {
        binding.changeInterestSelect16.setOnClickListener {
            selectOption16(true)
            checkToNext()
        }
        binding.changeInterestSelect16Dark.setOnClickListener {
            selectOption16(false)
            checkToNext()
        }
    }

    fun option17() {
        binding.changeInterestSelect17.setOnClickListener {
            selectOption17(true)
            checkToNext()
        }
        binding.changeInterestSelect17Dark.setOnClickListener {
            selectOption17(false)
            checkToNext()
        }
    }

    fun option18() {
        binding.changeInterestSelect18.setOnClickListener {
            selectOption18(true)
            checkToNext()
        }
        binding.changeInterestSelect18Dark.setOnClickListener {
            selectOption18(false)
            checkToNext()
        }
    }

    fun option19() {
        binding.changeInterestSelect19.setOnClickListener {
            selectOption19(true)
            checkToNext()
        }
        binding.changeInterestSelect19Dark.setOnClickListener {
            selectOption19(false)
            checkToNext()
        }
    }

    fun option20() {
        binding.changeInterestSelect20.setOnClickListener {
            selectOption20(true)
            checkToNext()
        }
        binding.changeInterestSelect20Dark.setOnClickListener {
            selectOption20(false)
            checkToNext()
        }
    }

    fun option21() {
        binding.changeInterestSelect21.setOnClickListener {
            selectOption21(true)
            checkToNext()
        }
        binding.changeInterestSelect21Dark.setOnClickListener {
            selectOption21(false)
            checkToNext()
        }
    }

    private fun checkToNext() {
        if (selectedOptions.size in 1..5) {
            binding.changeInterestNextBtnBefDark.visibility = View.VISIBLE
        }
    }
}

