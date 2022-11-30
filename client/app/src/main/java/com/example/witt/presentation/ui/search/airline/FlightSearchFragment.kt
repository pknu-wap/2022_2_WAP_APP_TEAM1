package com.example.witt.presentation.ui.search.airline

import android.os.Bundle
import android.view.View
import com.example.witt.R
import com.example.witt.presentation.base.BaseFragment
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.example.witt.databinding.FragmentFlightSearchBinding
import com.example.witt.presentation.ui.plan.PlanViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FlightSearchFragment: BaseFragment<FragmentFlightSearchBinding>(R.layout.fragment_flight_search){

    private val planViewModel by activityViewModels<PlanViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAirline()
        observeData()
    }

    private fun observeData() {
        planViewModel.planState.observe(viewLifecycleOwner) {
            it.TripId
            val sd = LocalDate.parse(it.StartDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            val ed = LocalDate.parse(it.EndDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            initDate(tripDateRange(sd,ed))
        }
    }

    //Date 리스트 생성
    private fun tripDateRange(sd:LocalDate,ed:LocalDate):Array<String>{
        var dateRange = mutableListOf<String>()
        var date = sd
        while(date<=ed){
            dateRange.add(date.toString())
            date = date.plusDays(1)
        }
        return dateRange.toTypedArray()
    }

    //항공사 선택 dialog 초기화
    private fun initAirline(){
        binding.airline.setOnClickListener{
            showAirlineDialog(it as TextView)
        }
    }

    private fun showAirlineDialog(it: TextView) {
        val airports = resources.getStringArray(R.array.airline_array)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("공항을 선택하세요.")
            .setItems(
                airports
            ) { _, which ->
                it.text = airports[which]
            }
        builder.show()
    }


    //Date 선택 dialog 초기화
    private fun initDate(dateRange:Array<String>){
        binding.date.setOnClickListener {
            showDateDialog(it as TextView,dateRange)
        }
    }

    private fun showDateDialog(it: TextView, dateRange: Array<String>){
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("날짜를 선택하세요.")
            .setItems(dateRange){
                _,which ->
                it.text = dateRange[which]
            }
        builder.show()
    }
}