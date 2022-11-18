package com.example.witt.presentation.ui.plan.make_plan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.witt.R
import com.example.witt.databinding.FragmentMakePlanBinding
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.presentation.ui.UiEvent
import com.example.witt.presentation.ui.plan.PlanViewModel
import com.example.witt.utils.convertTimestampToDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MakePlanFragment : BaseFragment<FragmentMakePlanBinding>(R.layout.fragment_make_plan) {

    private val viewModel : MakePlanViewModel by viewModels()
    private val planViewModel: PlanViewModel by activityViewModels()
    private val args by navArgs<MakePlanFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        observeData()
        initButton()
    }

    private fun initButton(){
        binding.goToDrawUpPlanButton.setOnClickListener{
            if(binding.planIdEditTextView.text.isNullOrBlank()) {
                binding.planIdTextField.error = "여행 이름을 붙여주세요!"
            }
            else {
                val startTime = args.startDate.convertTimestampToDate()
                val endTime = args.endDate.convertTimestampToDate()
                viewModel.submitPlan(startTime, endTime)
            }
        }
    }

    private fun observeData() {
        //목적지 설정
        viewModel.planDestination
            .flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach{
                binding.goToDrawUpPlanButton.isEnabled = true
                binding.goToDrawUpPlanButton.text = it
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        //일정 계획
        viewModel.makePlanEvent
            .flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach{ event ->
                when(event){
                    is UiEvent.Success ->{
                        planViewModel.setPlanState(event.data)
                        val direction =
                            MakePlanFragmentDirections.actionMakePlanFragmentToDrawUpPlanFragment()
                        findNavController().navigate(direction)
                    }
                    is UiEvent.Failure ->{
                        Toast.makeText(activity, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}