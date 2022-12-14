package com.example.witt.presentation.ui.home

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.util.Pair
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.witt.R
import com.example.witt.databinding.FragmentHomeBinding
import com.example.witt.domain.model.remote.plan.get_plan.toPlanStateModel
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.presentation.ui.UiEvent
import com.example.witt.presentation.ui.home.adapter.HomePlanAdapter
import com.example.witt.presentation.ui.plan.PlanViewModel
import com.example.witt.presentation.widget.JoinPlanDialog
import com.example.witt.presentation.widget.RemoveConfirmDialog
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val dateRangePicker by lazy {
        MaterialDatePicker.Builder.dateRangePicker()
            .setTheme(R.style.CustomThemeOverlay_MaterialCalendar_Fullscreen)
            .setTitleText("언제 여행을 떠나세요?")
            .setSelection(
                Pair(
                    MaterialDatePicker.todayInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()
                )
            )
            .build()
    }

    private val viewModel: HomeViewModel by viewModels()
    private val planViewModel: PlanViewModel by activityViewModels()

    private lateinit var homePlanAdapter: HomePlanAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initAdapter()
        initButton()
        initDateRangePicker()
        checkJoinPlan()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPlanList()
    }

    private fun observeData() {

        viewModel.planList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                homePlanAdapter.submitList(it.result)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.homeEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiEvent.Success -> { }
                    is UiEvent.Failure -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        // joinPlan
        viewModel.joinPlanUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    // joinPlanResult
                    is UiEvent.Success -> {
                        planViewModel.setPlanState(it.data)
                        val direction =
                            HomeFragmentDirections.actionHomeFragmentToDrawUpPlanFragment()
                        findNavController().navigate(direction)
                    }
                    is UiEvent.Failure -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initAdapter() {
        homePlanAdapter = HomePlanAdapter(
            onPlanCardClick = {
                planViewModel.setPlanState(it.toPlanStateModel())
                val direction =
                    HomeFragmentDirections.actionHomeFragmentToDrawUpPlanFragment()
                findNavController().navigate(direction)
            },
            // confirm dialog 확인 후 삭제
            onRemoveButtonClick = {
                RemoveConfirmDialog(
                    requireContext(),
                    onClickRemove = {
                        viewModel.removePlan(it)
                    },
                ).show()
            }
        )
        homePlanAdapter.submitList(emptyList())
        binding.homePlanRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.homePlanRecyclerView.adapter = homePlanAdapter
    }

    private fun initButton() {
        binding.goToMakePlanButton.setOnClickListener {
            dateRangePicker.show(requireActivity().supportFragmentManager, "datePicker")
        }
    }

    private fun initDateRangePicker() {
        // 백스택 후 재사용시 navigate 오류 발생
        dateRangePicker.addOnPositiveButtonClickListener {
            if (findNavController().currentDestination?.id == R.id.homeFragment) {
                val direction =
                    HomeFragmentDirections.actionHomeFragmentToMakePlanFragment(it.first, it.second)
                findNavController().navigate(direction)
            }
        }
    }

    private fun checkJoinPlan() {
        val prefs = requireActivity().getSharedPreferences("prefs", MODE_PRIVATE)
        val tripId = prefs.getString("tripId", null) ?: return
        val tripName = prefs.getString("tripName", null) ?: return
        val tripDate = prefs.getString("tripDate", null) ?: return
        showJoinPlanDialog(tripId, tripName, tripDate)
    }

    private fun showJoinPlanDialog(tripId: String, tripName: String, tripDate: String) {
        JoinPlanDialog(
            requireActivity(), tripName, tripDate,
            onClickCancel = {
                viewModel.rejectPlan()
            },
            onClickJoin = {
                viewModel.joinPlan(tripId.toInt())
            }
        ).show()
    }
}
