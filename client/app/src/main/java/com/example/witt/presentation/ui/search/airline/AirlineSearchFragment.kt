package com.example.witt.presentation.ui.search.airline

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import com.example.witt.R
import com.example.witt.databinding.FragmentAirlineSearchBinding
import com.example.witt.presentation.base.BaseFragment
import com.google.android.material.datepicker.MaterialDatePicker
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.witt.utils.convertTimestampToDate

class AirlineSearchFragment: BaseFragment<FragmentAirlineSearchBinding>(R.layout.fragment_airline_search){

    private val datePicker by lazy {
        MaterialDatePicker.Builder.datePicker()
            .setTheme(R.style.CustomThemeOverlay_MaterialCalendar_Fullscreen)
            .setTitleText("날짜를 선택해주세요!")
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
        initDatePicker()
        initNumberPickers()
    }

    private fun initButton(){
        binding.datePeriod.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "datePicker")
        }
        binding.airportStart.setOnClickListener{
            showDialog(it as TextView)
        }
        binding.airportArive.setOnClickListener{
            showDialog(it as TextView)
        }
    }

    private fun initDatePicker() {
        datePicker.addOnPositiveButtonClickListener {
            binding.datePeriod.text = it.convertTimestampToDate()
        }
    }

    private fun showDialog(it: TextView){
        val airports = resources.getStringArray(R.array.airline_array)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("공항을 선택하세요.")
            .setItems(airports
            ) { dialog, which ->
                it.text = airports[which]
            }
        builder.show()
    }

    private fun initNumberPickers(){
        val numberPickers = arrayListOf(
            binding.adultNumberPicker, binding.childNumberPicker, binding.babyNumberPicker)
        for (index in numberPickers.indices) {
            initNumberPicker(numberPickers[index])
        }
    }

    // 넘버 픽커 초기화
    private fun initNumberPicker(numberPicker: NumberPicker){
        with(numberPicker){
            minValue = 0
            maxValue = 9
            wrapSelectorWheel = false
            numberPickerListener(this)
        }
    }

//     넘버 픽커 리스너
    private fun numberPickerListener(numberPicker:NumberPicker){
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
//            Log.d("test", "oldVal : ${oldVal}, newVal : $newVal")
//            Log.d("test", "picker.displayedValues ${picker.displayedValues[picker.value]}")
        }
    }

}