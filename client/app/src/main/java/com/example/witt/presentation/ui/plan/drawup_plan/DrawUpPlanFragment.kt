package com.example.witt.presentation.ui.plan.drawup_plan

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.witt.R
import com.example.witt.databinding.FragmentDrawUpPlanBinding
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.presentation.ui.plan.drawup_plan.adapter.DatePlanAdapter
import com.example.witt.presentation.ui.plan.drawup_plan.adapter.TimePlanAdapter
import com.example.witt.presentation.ui.plan.drawup_plan.example.PlanDummy

class DrawUpPlanFragment  : BaseFragment<FragmentDrawUpPlanBinding>(R.layout.fragment_draw_up_plan) {

    private lateinit var timePlanAdapter: TimePlanAdapter
    private lateinit var datePlanAdapter: DatePlanAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initView()
    }

    private fun initView(){
        binding.dateTextView.text = "2022.11.21 ~ 2022.11.23"
        binding.destinationTextView.text = "부산/경상"
        binding.planNameTextView.text = "성훈쿤의 생일기념 여행"
    }

    private fun initAdapter(){
        //시간순 어댑터
        timePlanAdapter = TimePlanAdapter(
            memoClick = {
                //todo memo 수정 페이지 전환
            }
        )

        timePlanAdapter.submitList(PlanDummy.getTimePlan())


        //날짜 어댑터
        datePlanAdapter = DatePlanAdapter(
            context = requireContext(),
            timePlanAdapter = timePlanAdapter,
            memoButtonClick = {
                val direction = DrawUpPlanFragmentDirections.actionDrawUpPlanFragmentToWriteMemoFragment()
                findNavController().navigate(direction)
            },
            placeButtonClick = {
               //todo place 작성페이지 전환
            }
        )
        datePlanAdapter.submitList(PlanDummy.getDatePlan())
        binding.planRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.planRecyclerView.adapter = datePlanAdapter

    }
}