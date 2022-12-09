package com.example.witt.presentation.ui.plan.drawup_plan

import android.content.ActivityNotFoundException
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.witt.R
import com.example.witt.databinding.FragmentDrawUpPlanBinding
import com.example.witt.domain.model.use_case.plan.PlaceInfo
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.presentation.ui.UiEvent
import com.example.witt.presentation.ui.UiState
import com.example.witt.presentation.ui.plan.PlanViewModel
import com.example.witt.presentation.ui.plan.drawup_plan.adapter.ParticipantAdapter
import com.example.witt.presentation.ui.plan.drawup_plan.adapter.PlanAdapter
import com.example.witt.presentation.ui.plan.drawup_plan.memo_dialog.WriteMemoFragment
import com.example.witt.presentation.widget.RemoveConfirmDialog
import com.example.witt.utils.DefaultTemplate
import com.example.witt.utils.convertCoordinates
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.FeedTemplate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class DrawUpPlanFragment : BaseFragment<FragmentDrawUpPlanBinding>(R.layout.fragment_draw_up_plan) {

    private lateinit var planAdapter: PlanAdapter
    private lateinit var participantAdapter: ParticipantAdapter

    private val planViewModel by activityViewModels<PlanViewModel>()
    private val viewModel: DrawUpViewModel by viewModels()
    private lateinit var mapView: MapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
        initMap()
        initAdapter()
        observeData()
    }

    private fun initButton() {
        binding.sharePlanButton.setOnClickListener {
            // todo refactor
            planViewModel.planState.value?.let {
                sendKakaoLink(requireContext(), DefaultTemplate.createTemplate(it))
            } ?: Toast.makeText(
                requireContext(), "카카오톡 링크를 생성하는데 실패하였습니다.",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.outPlanButton.setOnClickListener {
            RemoveConfirmDialog(
                onClickRemove = {
                    viewModel.outPlan()
                },
                onClickCancel = {},
                context = requireContext()
            ).show()
        }

        binding.addAirlineButton.setOnClickListener {
            val direction = DrawUpPlanFragmentDirections.actionDrawUpPlanFragmentToFlightSearchFragment()
            findNavController().navigate(direction)
        }

        binding.goChatButton.setOnClickListener {
            val direction = DrawUpPlanFragmentDirections.actionDrawUpPlanFragmentToChatFragment()
            findNavController().navigate(direction)
        }
    }

    private fun observeData() {
        binding.viewModel = planViewModel
        // planViewModel에서 데이터 가져오기
        planViewModel.planState.observe(viewLifecycleOwner) {
            viewModel.getDetailPlan(it)
            // default 좌표 설정
            val coordinate = it.Region.convertCoordinates()
            setMarker(listOf(PlaceInfo(it.Region, coordinate.first, coordinate.second)))
        }

        viewModel.drawUpPlanEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiEvent.Success -> {
                        val direction =
                            DrawUpPlanFragmentDirections.actionDrawUpPlanFragmentToHomeFragment()
                        findNavController().navigate(direction)
                    }
                    is UiEvent.Failure -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.drawUpPlanData.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> {
                        planAdapter.submitList(it.data)
                    }
                    is UiState.Failure -> {}
                    is UiState.Init -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        // 여기서 Map 작업 및 다른 작업 하시면 됩니다~
        viewModel.planData.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> {
                        it.data.participants?.let { participantList ->
                            participantAdapter.submitList(participantList)
                        }
                        val data : MutableList<PlaceInfo> = mutableListOf()
                        it.data.plans?.forEach{ plan ->
                            plan.place?.let{ place ->
                                data.add(PlaceInfo(place.name, place.latitude, place.longitude))
                            }
                        }
                        if(data.isNotEmpty()){ setMarker(data) }
                    }
                    is UiState.Failure -> {}
                    is UiState.Init -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initAdapter() {
        planAdapter = PlanAdapter(
            context = requireContext(),
            memoClick = { showMemoDialog(it.day, it.planId, it.memo?.content) },
            placeClick = {
                it.place?.let{ place ->
                    val mapPoint = MapPoint.mapPointWithGeoCoord(place.latitude, place.longitude)
                    mapView.setMapCenterPoint(mapPoint, true)
                }
            },
            memoButtonClick = { day ->
                showMemoDialog(day, null, null)
            },
            placeButtonClick = { day ->
                val direction = DrawUpPlanFragmentDirections.actionDrawUpPlanFragmentToMapSearchFragment(day)
                findNavController().navigate(direction)
            },
        )
        participantAdapter = ParticipantAdapter()

        with(binding) {
            participantRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            participantRecyclerView.adapter = participantAdapter
            datePlanRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            datePlanRecyclerView.adapter = planAdapter
        }
    }

    private fun showMemoDialog(day: Int, planId: Int?, memo: String?) {
        val memoDialog = WriteMemoFragment(
            onClick = {
                viewModel.refreshPlan()
            }
        )
        val args = Bundle()

        args.putInt("day", day)
        memo?.let { args.putString("memo", it) }
        planId?.let { args.putInt("planId", it) }

        memoDialog.arguments = args
        memoDialog.show(requireActivity().supportFragmentManager, "MEMO")
    }

    private fun initMap() {
        mapView = MapView(requireActivity())
        binding.mapView.addView(mapView)
    }

    private fun setMarker(placeInfo: List<PlaceInfo>){
        mapView.removeAllPOIItems()
        placeInfo.forEach{ place ->
            val marker = MapPOIItem()
            with(marker) {
                itemName = place.name // 머커에 표시되는 이름
                tag = 0
                mapPoint = MapPoint.mapPointWithGeoCoord(
                    place.latitude,
                    place.longitude
                ) // 마커 위치
                markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
                selectedMarkerType =
                    MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            }
            mapView.addPOIItem(marker)
        }
        val mapPoint = MapPoint.mapPointWithGeoCoord(placeInfo.first().latitude, placeInfo.first().longitude)
        mapView.setMapCenterPoint(mapPoint, true)
    }

    private fun sendKakaoLink(context: Context, defaultFeed: FeedTemplate) {
        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            ShareClient.instance.shareDefault(context, defaultFeed) { sharingResult, _ ->
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
