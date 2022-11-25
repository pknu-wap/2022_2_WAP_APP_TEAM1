package com.example.witt.presentation.ui.search.airline

import android.os.Bundle
import android.view.View
import androidx.core.util.Pair
import com.example.witt.R
import com.example.witt.databinding.FragmentAirlineSearchBinding
import com.example.witt.presentation.base.BaseFragment
import com.google.android.material.datepicker.MaterialDatePicker

class AirlineSearchFragment: BaseFragment<FragmentAirlineSearchBinding>(R.layout.fragment_airline_search){

    private val dateRangePicker by lazy {
        MaterialDatePicker.Builder.dateRangePicker()
            .setTheme(R.style.CustomThemeOverlay_MaterialCalendar_Fullscreen)
            .setTitleText("출발하는 날짜를 선택해주세요!")
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
    }

    private fun initButton(){
        binding.dateBtn.setOnClickListener {
            dateRangePicker.show(requireActivity().supportFragmentManager, "datePicker")
        }
    }
}