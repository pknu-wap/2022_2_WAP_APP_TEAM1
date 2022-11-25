package com.example.witt.presentation.ui.search.airline

import android.os.Bundle
import android.view.View
import com.example.witt.R
import com.example.witt.databinding.FragmentAirlineSearchBinding
import com.example.witt.presentation.base.BaseFragment
import com.google.android.material.datepicker.MaterialDatePicker
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

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
            binding.datePeriod.text= formattingDate(it)
        }
    }

    private fun formattingDate(time:Long):String{
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(date)
    }

    private fun showDialog(it: TextView){
        val airports = resources.getStringArray(R.array.airport_array)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("공항을 선택하세요.")
            .setItems(airports
            ) { dialog, which ->
                it.text = airports[which]
            }
        builder.show()
    }

}