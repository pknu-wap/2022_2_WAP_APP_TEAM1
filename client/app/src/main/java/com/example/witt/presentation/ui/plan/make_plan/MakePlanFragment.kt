package com.example.witt.presentation.ui.plan.make_plan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.witt.R
import com.example.witt.databinding.FragmentMakePlanBinding
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.utils.convertTimestampToDate
import kotlinx.coroutines.launch


class MakePlanFragment : BaseFragment<FragmentMakePlanBinding>(R.layout.fragment_make_plan) {

    private val viewModel : MakePlanViewModel by viewModels()
    private val args by navArgs<MakePlanFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initDates()
        observeData()
        binding.goToDrawUpPlanButton.setOnClickListener{
            val direction = MakePlanFragmentDirections.actionMakePlanFragmentToDrawUpPlanFragment()
            findNavController().navigate(direction)
        }
    }

    private fun initDates(){
        val startTime = args.startDate.convertTimestampToDate()
        val endTime = args.endDate.convertTimestampToDate()
       viewModel.onEvent(MakePlanEvent.SubmitPlanDate(startTime, endTime))
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.planDestination.observe(viewLifecycleOwner){
                    binding.goToDrawUpPlanButton.isEnabled = true
                    binding.goToDrawUpPlanButton.text = it
                }
            }
        }
    }
}