package com.example.witt.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.util.Pair
import androidx.navigation.fragment.findNavController
import com.example.witt.R
import com.example.witt.databinding.FragmentHomeBinding
import com.example.witt.presentation.base.BaseFragment
import com.google.android.material.datepicker.MaterialDatePicker

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val dateRangePicker by lazy {
        MaterialDatePicker.Builder.dateRangePicker()
            .setTheme(R.style.CustomThemeOverlay_MaterialCalendar_Fullscreen)
            .setTitleText("언제 여행을 떠나세요?")
            .setSelection(
                Pair(
                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()
                )
            )
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
        initDateRangePicker()
    }

    private fun initButton(){
        binding.goToMakePlanButton.setOnClickListener {
            dateRangePicker.show(requireActivity().supportFragmentManager, "datePicker")
        }
    }
    private fun initDateRangePicker() {
        //todo 백스택 후 재사용시 navigate 오류 발생
        dateRangePicker.addOnPositiveButtonClickListener {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToMakePlanFragment(it.first, it.second)
            findNavController().navigate(direction)
        }
    }
}