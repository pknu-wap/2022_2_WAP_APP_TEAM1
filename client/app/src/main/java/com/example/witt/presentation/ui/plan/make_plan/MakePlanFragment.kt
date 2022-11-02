package com.example.witt.presentation.ui.plan.make_plan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.witt.R
import com.example.witt.databinding.FragmentMakePlanBinding
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.utils.convertTimestampToDate


class MakePlanFragment : BaseFragment<FragmentMakePlanBinding>(R.layout.fragment_make_plan) {

    private val viewModel : MakePlanViewModel by viewModels()
    private val args  by navArgs<MakePlanFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDates()
    }

    private fun initDates(){
        val startTime = args.startDate.convertTimestampToDate()
        val endTime = args.endDate.convertTimestampToDate()
       viewModel.onEvent(MakePlanEvent.SubmitPlanDate(startTime, endTime))
    }

    private fun initDestinationButton(){

    }

}