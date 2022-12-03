package com.example.witt.presentation.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.example.witt.data.repository.FlightRepositoryImpl
import com.example.witt.databinding.DialogSearchFlightBinding
import com.example.witt.domain.model.remote.detail_plan.search.SearchFlightModel
import com.example.witt.domain.model.remote.detail_plan.search.toAddFlightRequest
import kotlinx.coroutines.*

class AddFlightDialog(
    context: Context,
    private val result: SearchFlightModel,
    private val tripId:Int,
    private val onClickApprove: () -> Unit = {},
    private val onClickCancel: () -> Unit = {}
) : Dialog(context) {

    private val repository = FlightRepositoryImpl()

    private val binding by lazy { DialogSearchFlightBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)
        initText(result)

        binding.dialogApproveButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                addFlightRepo()
                withContext(Dispatchers.Main){
                    onClickApprove()
                }
            }
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

    private fun initText(result: SearchFlightModel){
        binding.departureAirline.text = result.Flight.flyFrom
        binding.ariveAirline.text = result.Flight.flyTo
        binding.flightNum.text = result.Flight.flightNo.toString()
        binding.airlineText.text = result.Flight.airline
        binding.departureTime.text = result.Flight.departure
        binding.ariveTime.text = result.Flight.arrival
    }
    private suspend fun addFlightRepo(){
        val result = repository.addFlight(tripId, result.Flight.toAddFlightRequest())
        Log.d("test",result.toString() )
    }
    private fun onClickCancel (){

    }
}