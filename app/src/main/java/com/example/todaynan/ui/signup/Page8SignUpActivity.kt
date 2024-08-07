package com.example.todaynan.ui.signup

import android.view.View
import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.SignupData
import com.example.todaynan.databinding.SignupPage8Binding
import com.example.todaynan.ui.BaseActivity

class Page8SignUpActivity : BaseActivity<SignupPage8Binding>(SignupPage8Binding::inflate) {

    private val selectedOptions = mutableListOf<String>()
    private val selectedOptionsIndex = mutableListOf<Int>()

    override fun initAfterBinding() {

        binding.signupPage8Back.setOnClickListener {
            finish()
        }

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

        // 선택한 항목이 5개이하 일때만 다음 화면으로 넘어갈 수 있도록 함
        binding.signupNextBtnBefDark.setOnClickListener {
            if (selectedOptions.size in 1..5) {
                SignupData.selectedOptions.clear()
                SignupData.selectedOptions.addAll(selectedOptions)
                AppData.preferStr = selectedOptions as ArrayList<String>
                AppData.preferIdx = selectedOptionsIndex as List<Long>

                /*val status = true
                val intent = Intent(this, Page1SignUpActivity::class.java)
                intent.putExtra("status", status)
                startActivity(intent)*/
                finish()
            }
        }

    }

    fun selectOption1(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect1Dark.visibility = View.VISIBLE
                binding.signupSelect1.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect1Tv.text.toString())
                selectedOptionsIndex.add(0)
            }
        } else {
            binding.signupSelect1Dark.visibility = View.INVISIBLE
            binding.signupSelect1.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect1Tv.text.toString())
            selectedOptionsIndex.remove(0)
        }
    }

    fun selectOption2(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect2Dark.visibility = View.VISIBLE
                binding.signupSelect2.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect2Tv.text.toString())
                selectedOptionsIndex.add(1)
            }
        } else {
            binding.signupSelect2Dark.visibility = View.INVISIBLE
            binding.signupSelect2.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect2Tv.text.toString())
            selectedOptionsIndex.remove(1)
        }
    }

    fun selectOption3(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect3Dark.visibility = View.VISIBLE
                binding.signupSelect3.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect3Tv.text.toString())
                selectedOptionsIndex.add(2)
            }
        } else {
            binding.signupSelect3Dark.visibility = View.INVISIBLE
            binding.signupSelect3.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect3Tv.text.toString())
            selectedOptionsIndex.remove(2)
        }
    }

    fun selectOption4(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect4Dark.visibility = View.VISIBLE
                binding.signupSelect4.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect4Tv.text.toString())
                selectedOptionsIndex.add(3)
            }
        } else {
            binding.signupSelect4Dark.visibility = View.INVISIBLE
            binding.signupSelect4.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect4Tv.text.toString())
            selectedOptionsIndex.remove(3)
        }
    }

    fun selectOption5(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect5Dark.visibility = View.VISIBLE
                binding.signupSelect5.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect5Tv.text.toString())
                selectedOptionsIndex.add(4)
            }
        } else {
            binding.signupSelect5Dark.visibility = View.INVISIBLE
            binding.signupSelect5.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect5Tv.text.toString())
            selectedOptionsIndex.remove(4)
        }
    }

    fun selectOption6(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect6Dark.visibility = View.VISIBLE
                binding.signupSelect6.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect6Tv.text.toString())
                selectedOptionsIndex.add(5)
            }
        } else {
            binding.signupSelect6Dark.visibility = View.INVISIBLE
            binding.signupSelect6.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect6Tv.text.toString())
            selectedOptionsIndex.remove(5)
        }
    }

    fun selectOption7(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect7Dark.visibility = View.VISIBLE
                binding.signupSelect7.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect7Tv.text.toString())
                selectedOptionsIndex.add(6)
            }
        } else {
            binding.signupSelect7Dark.visibility = View.INVISIBLE
            binding.signupSelect7.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect7Tv.text.toString())
            selectedOptionsIndex.remove(6)
        }
    }

    fun selectOption8(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect8Dark.visibility = View.VISIBLE
                binding.signupSelect8.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect8Tv.text.toString())
                selectedOptionsIndex.add(7)
            }
        } else {
            binding.signupSelect8Dark.visibility = View.INVISIBLE
            binding.signupSelect8.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect8Tv.text.toString())
            selectedOptionsIndex.remove(7)
        }
    }

    fun selectOption9(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect9Dark.visibility = View.VISIBLE
                binding.signupSelect9.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect9Tv.text.toString())
                selectedOptionsIndex.add(8)
            }
        } else {
            binding.signupSelect9Dark.visibility = View.INVISIBLE
            binding.signupSelect9.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect9Tv.text.toString())
            selectedOptionsIndex.remove(8)
        }
    }

    fun selectOption10(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect10Dark.visibility = View.VISIBLE
                binding.signupSelect10.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect10Tv.text.toString())
                selectedOptionsIndex.add(9)
            }
        } else {
            binding.signupSelect10Dark.visibility = View.INVISIBLE
            binding.signupSelect10.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect10Tv.text.toString())
            selectedOptionsIndex.remove(9)
        }
    }

    fun selectOption11(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect11Dark.visibility = View.VISIBLE
                binding.signupSelect11.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect11Tv.text.toString())
                selectedOptionsIndex.add(10)
            }
        } else {
            binding.signupSelect11Dark.visibility = View.INVISIBLE
            binding.signupSelect11.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect11Tv.text.toString())
            selectedOptionsIndex.remove(10)
        }
    }

    fun selectOption12(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect12Dark.visibility = View.VISIBLE
                binding.signupSelect12.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect12Tv.text.toString())
                selectedOptionsIndex.add(11)
            }
        } else {
            binding.signupSelect12Dark.visibility = View.INVISIBLE
            binding.signupSelect12.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect12Tv.text.toString())
            selectedOptionsIndex.remove(11)
        }
    }

    fun selectOption13(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect13Dark.visibility = View.VISIBLE
                binding.signupSelect13.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect13Tv.text.toString())
                selectedOptionsIndex.add(12)
            }
        } else {
            binding.signupSelect13Dark.visibility = View.INVISIBLE
            binding.signupSelect13.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect13Tv.text.toString())
            selectedOptionsIndex.remove(12)
        }
    }

    fun selectOption14(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect14Dark.visibility = View.VISIBLE
                binding.signupSelect14.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect14Tv.text.toString())
                selectedOptionsIndex.add(13)
            }
        } else {
            binding.signupSelect14Dark.visibility = View.INVISIBLE
            binding.signupSelect14.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect14Tv.text.toString())
            selectedOptionsIndex.remove(13)
        }
    }

    fun selectOption15(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect15Dark.visibility = View.VISIBLE
                binding.signupSelect15.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect15Tv.text.toString())
                selectedOptionsIndex.add(14)
            }
        } else {
            binding.signupSelect15Dark.visibility = View.INVISIBLE
            binding.signupSelect15.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect15Tv.text.toString())
            selectedOptionsIndex.remove(14)
        }
    }

    fun selectOption16(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect16Dark.visibility = View.VISIBLE
                binding.signupSelect16.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect16Tv.text.toString())
                selectedOptionsIndex.add(15)
            }
        } else {
            binding.signupSelect16Dark.visibility = View.INVISIBLE
            binding.signupSelect16.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect16Tv.text.toString())
            selectedOptionsIndex.remove(15)
        }
    }

    fun selectOption17(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect17Dark.visibility = View.VISIBLE
                binding.signupSelect17.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect17Tv.text.toString())
                selectedOptionsIndex.add(16)
            }
        } else {
            binding.signupSelect17Dark.visibility = View.INVISIBLE
            binding.signupSelect17.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect17Tv.text.toString())
            selectedOptionsIndex.remove(16)
        }
    }

    fun selectOption18(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect18Dark.visibility = View.VISIBLE
                binding.signupSelect18.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect18Tv.text.toString())
                selectedOptionsIndex.add(17)
            }
        } else {
            binding.signupSelect18Dark.visibility = View.INVISIBLE
            binding.signupSelect18.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect18Tv.text.toString())
            selectedOptionsIndex.remove(17)
        }
    }

    fun selectOption19(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect19Dark.visibility = View.VISIBLE
                binding.signupSelect19.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect19Tv.text.toString())
                selectedOptionsIndex.add(18)
            }
        } else {
            binding.signupSelect19Dark.visibility = View.INVISIBLE
            binding.signupSelect19.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect19Tv.text.toString())
            selectedOptionsIndex.remove(18)
        }
    }

    fun selectOption20(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect20Dark.visibility = View.VISIBLE
                binding.signupSelect20.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect20Tv.text.toString())
                selectedOptionsIndex.add(19)
            }
        } else {
            binding.signupSelect20Dark.visibility = View.INVISIBLE
            binding.signupSelect20.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect20Tv.text.toString())
            selectedOptionsIndex.remove(19)
        }
    }

    fun selectOption21(isSelect: Boolean) {
        if (isSelect) {
            if (selectedOptions.size < 5) {
                binding.signupSelect21Dark.visibility = View.VISIBLE
                binding.signupSelect21.visibility = View.INVISIBLE
                selectedOptions.add(binding.signupSelect21Tv.text.toString())
                selectedOptionsIndex.add(20)
            }
        } else {
            binding.signupSelect21Dark.visibility = View.INVISIBLE
            binding.signupSelect21.visibility = View.VISIBLE
            selectedOptions.remove(binding.signupSelect21Tv.text.toString())
            selectedOptionsIndex.remove(20)
        }
    }

    fun option1() {
        binding.signupSelect1.setOnClickListener {
            selectOption1(true)
            checkToNext()
        }
        binding.signupSelect1Dark.setOnClickListener {
            selectOption1(false)
            checkToNext()
        }
    }

    fun option2() {
        binding.signupSelect2.setOnClickListener {
            selectOption2(true)
            checkToNext()
        }
        binding.signupSelect2Dark.setOnClickListener {
            selectOption2(false)
            checkToNext()
        }
    }

    fun option3() {
        binding.signupSelect3.setOnClickListener {
            selectOption3(true)
            checkToNext()
        }
        binding.signupSelect3Dark.setOnClickListener {
            selectOption3(false)
            checkToNext()
        }
    }

    fun option4() {
        binding.signupSelect4.setOnClickListener {
            selectOption4(true)
            checkToNext()
        }
        binding.signupSelect4Dark.setOnClickListener {
            selectOption4(false)
            checkToNext()
        }
    }

    fun option5() {
        binding.signupSelect5.setOnClickListener {
            selectOption5(true)
            checkToNext()
        }
        binding.signupSelect5Dark.setOnClickListener {
            selectOption5(false)
            checkToNext()
        }
    }

    fun option6() {
        binding.signupSelect6.setOnClickListener {
            selectOption6(true)
            checkToNext()
        }
        binding.signupSelect6Dark.setOnClickListener {
            selectOption6(false)
            checkToNext()
        }
    }

    fun option7() {
        binding.signupSelect7.setOnClickListener {
            selectOption7(true)
            checkToNext()
        }
        binding.signupSelect7Dark.setOnClickListener {
            selectOption7(false)
            checkToNext()
        }
    }

    fun option8() {
        binding.signupSelect8.setOnClickListener {
            selectOption8(true)
            checkToNext()
        }
        binding.signupSelect8Dark.setOnClickListener {
            selectOption8(false)
            checkToNext()
        }
    }

    fun option9() {
        binding.signupSelect9.setOnClickListener {
            selectOption9(true)
            checkToNext()
        }
        binding.signupSelect9Dark.setOnClickListener {
            selectOption9(false)
            checkToNext()
        }
    }

    fun option10() {
        binding.signupSelect10.setOnClickListener {
            selectOption10(true)
            checkToNext()
        }
        binding.signupSelect10Dark.setOnClickListener {
            selectOption10(false)
            checkToNext()
        }
    }

    fun option11() {
        binding.signupSelect11.setOnClickListener {
            selectOption11(true)
            checkToNext()
        }
        binding.signupSelect11Dark.setOnClickListener {
            selectOption11(false)
            checkToNext()
        }
    }

    fun option12() {
        binding.signupSelect12.setOnClickListener {
            selectOption12(true)
            checkToNext()
        }
        binding.signupSelect12Dark.setOnClickListener {
            selectOption12(false)
            checkToNext()
        }
    }

    fun option13() {
        binding.signupSelect13.setOnClickListener {
            selectOption13(true)
            checkToNext()
        }
        binding.signupSelect13Dark.setOnClickListener {
            selectOption13(false)
            checkToNext()
        }
    }

    fun option14() {
        binding.signupSelect14.setOnClickListener {
            selectOption14(true)
            checkToNext()
        }
        binding.signupSelect14Dark.setOnClickListener {
            selectOption14(false)
            checkToNext()
        }
    }

    fun option15() {
        binding.signupSelect15.setOnClickListener {
            selectOption15(true)
            checkToNext()
        }
        binding.signupSelect15Dark.setOnClickListener {
            selectOption15(false)
            checkToNext()
        }
    }

    fun option16() {
        binding.signupSelect16.setOnClickListener {
            selectOption16(true)
            checkToNext()
        }
        binding.signupSelect16Dark.setOnClickListener {
            selectOption16(false)
            checkToNext()
        }
    }

    fun option17() {
        binding.signupSelect17.setOnClickListener {
            selectOption17(true)
            checkToNext()
        }
        binding.signupSelect17Dark.setOnClickListener {
            selectOption17(false)
            checkToNext()
        }
    }

    fun option18() {
        binding.signupSelect18.setOnClickListener {
            selectOption18(true)
            checkToNext()
        }
        binding.signupSelect18Dark.setOnClickListener {
            selectOption18(false)
            checkToNext()
        }
    }

    fun option19() {
        binding.signupSelect19.setOnClickListener {
            selectOption19(true)
            checkToNext()
        }
        binding.signupSelect19Dark.setOnClickListener {
            selectOption19(false)
            checkToNext()
        }
    }

    fun option20() {
        binding.signupSelect20.setOnClickListener {
            selectOption20(true)
            checkToNext()
        }
        binding.signupSelect20Dark.setOnClickListener {
            selectOption20(false)
            checkToNext()
        }
    }

    fun option21() {
        binding.signupSelect21.setOnClickListener {
            selectOption21(true)
            checkToNext()
        }
        binding.signupSelect21Dark.setOnClickListener {
            selectOption21(false)
            checkToNext()
        }
    }

    private fun checkToNext() {
        if (selectedOptions.size in 1..5) {
            binding.signupNextBtnBefDark.visibility = View.VISIBLE
        }
    }
}
