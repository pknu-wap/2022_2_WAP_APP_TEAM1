package com.example.witt.presentation.ui.plan.drawup_plan

import android.content.ActivityNotFoundException
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.witt.R
import com.example.witt.databinding.FragmentDrawUpPlanBinding
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.presentation.ui.plan.PlanViewModel
import com.example.witt.presentation.ui.plan.drawup_plan.adapter.DatePlanAdapter
import com.example.witt.presentation.ui.plan.drawup_plan.adapter.TimePlanAdapter
import com.example.witt.presentation.ui.plan.drawup_plan.example.PlanDummy
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import com.example.witt.presentation.ui.plan.drawup_plan.memo_dialog.WriteMemoFragment
import com.example.witt.utils.DefaultTemplate
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.*
import net.daum.mf.map.api.MapView


class DrawUpPlanFragment  : BaseFragment<FragmentDrawUpPlanBinding>(R.layout.fragment_draw_up_plan) {

    private lateinit var timePlanAdapter: TimePlanAdapter
    private lateinit var datePlanAdapter: DatePlanAdapter
    private val viewModel by activityViewModels<PlanViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        //initMap()
        initAdapter()
        initButton()
    }

    private fun initButton() {
        binding.sharePlanButton.setOnClickListener {
            sendKakaoLink(requireContext(), DefaultTemplate.kakaoTemplate)
        }
    }

    private fun initAdapter() {
        //시간순 어댑터
        timePlanAdapter = TimePlanAdapter(memoClick = { showMemoDialog(it.memo) })
        timePlanAdapter.submitList(PlanDummy.getTimePlan())

        //날짜 어댑터
        datePlanAdapter = DatePlanAdapter(
            context = requireContext(),
            timePlanAdapter = timePlanAdapter,
            memoButtonClick = {
                showMemoDialog(null)
            },
            placeButtonClick = {
                val direction = DrawUpPlanFragmentDirections.actionDrawUpPlanFragmentToMapSearchFragment()
                findNavController().navigate(direction)
            }
        )
        datePlanAdapter.submitList(PlanDummy.getDatePlan())
        binding.datePlanRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.datePlanRecyclerView.adapter = datePlanAdapter

    }


    private fun initMap() {
        val mapView by lazy { MapView(requireActivity()) }
        binding.mapView.addView(mapView)
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);
        mapView.setZoomLevel(5, true)
        val marker = MapPOIItem()
        marker.itemName = "Default Marker"
        marker.tag = 0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633)
        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.

        marker.selectedMarkerType =
            MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker)

    }

    private fun showMemoDialog(memo: String?) {
        val memoDialog = WriteMemoFragment()
        memo?.let {
            val args = Bundle()
            args.putString("memo", it)
            memoDialog.arguments = args
        }
        memoDialog.show(requireActivity().supportFragmentManager, "MEMO")
    }

    private fun sendKakaoLink(context: Context, defaultFeed: FeedTemplate) {
        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            ShareClient.instance.shareDefault(context, defaultFeed) { sharingResult, error ->
                if (sharingResult != null) {
                    Log.d("tag", "카카오톡 공유 성공 ${sharingResult.intent}")
                    startActivity(sharingResult.intent)
                }
            }
        } else { // 웹 공유 예시 코드
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)
            try {
                KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
            } catch (e: UnsupportedOperationException) {
                e.printStackTrace()
            }
            try {
                KakaoCustomTabsClient.open(context, sharerUrl)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}