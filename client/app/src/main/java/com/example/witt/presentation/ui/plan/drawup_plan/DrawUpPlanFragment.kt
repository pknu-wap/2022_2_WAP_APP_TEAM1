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
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class DrawUpPlanFragment  : BaseFragment<FragmentDrawUpPlanBinding>(R.layout.fragment_draw_up_plan) {

    private lateinit var timePlanAdapter: TimePlanAdapter
    private lateinit var datePlanAdapter: DatePlanAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMap()
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
                val direction = DrawUpPlanFragmentDirections.actionDrawUpPlanFragmentToMapSearchFragment()
                findNavController().navigate(direction)
            }
        )
        datePlanAdapter.submitList(PlanDummy.getDatePlan())
        binding.planRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.planRecyclerView.adapter = datePlanAdapter

    }

    private fun initMap(){
        val mapView by lazy { MapView(requireActivity()) }
        binding.mapView.addView(mapView)
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);
        mapView.setZoomLevel(5,true)
        val marker = MapPOIItem()
        marker.itemName = "Default Marker"
        marker.tag = 0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633)
        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.

        marker.selectedMarkerType =
        MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker)
    }
}