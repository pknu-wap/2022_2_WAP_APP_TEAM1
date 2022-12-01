package com.example.witt.presentation.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.witt.databinding.DialogSearchFlightBinding
import com.example.witt.domain.model.flight.SearchFlightModel

class AddFlightDialog(
    context: Context,
    private val result:SearchFlightModel
) : Dialog(context) {

    private val binding by lazy { DialogSearchFlightBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)
        initText(result)

        binding.dialogApproveButton.setOnClickListener {
            onClickApprove()
            dismiss()
        }

        binding.dialogCancelButton.setOnClickListener {
            onClickCancel()
            dismiss()
        }
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun initText(result:SearchFlightModel){
        binding.departureAirline.text = result.Flight.flyFrom
        binding.ariveAirline.text = result.Flight.flyTo
        binding.flightNum.text = result.Flight.flightNo.toString()
        binding.airlineText.text = result.Flight.airline
        binding.departureTime.text = result.Flight.departure
        binding.ariveTime.text = result.Flight.arrival
    }
    private fun onClickApprove(){

    }
    private fun onClickCancel (){

    }
}