package com.example.witt.presentation.ui.plan.drawup_plan

import android.content.ActivityNotFoundException
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
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
import com.example.witt.presentation.listener.MarkerEventListener
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
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapPolyline

@AndroidEntryPoint
class DrawUpPlanFragment : BaseFragment<FragmentDrawUpPlanBinding>(R.layout.fragment_draw_up_plan) {

    private lateinit var planAdapter: PlanAdapter
    private lateinit var participantAdapter: ParticipantAdapter

    private val planViewModel by activityViewModels<PlanViewModel>()
    private val viewModel: DrawUpViewModel by viewModels()

    private val eventListener by lazy { MarkerEventListener(requireActivity()) }
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
                requireContext(), "???????????? ????????? ??????????????? ?????????????????????.",
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
        // planViewModel?????? ????????? ????????????
        planViewModel.planState.observe(viewLifecycleOwner) {
            viewModel.getDetailPlan(it)
            // default ?????? ??????
            val coordinate = it.Region.convertCoordinates()
            setMarker(listOf(PlaceInfo(it.Region, coordinate.first, coordinate.second, "")))
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

        // ????????? Map ?????? ??? ?????? ?????? ????????? ?????????~
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
                                data.add(PlaceInfo(place.name, place.latitude,
                                    place.longitude, place.category))
                            }
                        }
                        if(data.isNotEmpty()){
                            setMarker(data)
                            setPolyLine(data)
                        }
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
        mapView.setPOIItemEventListener(eventListener)
        mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))
    }

    private fun setMarker(placeInfo: List<PlaceInfo>){
        mapView.removeAllPOIItems()
        placeInfo.forEach{ place ->
            val marker = MapPOIItem()
            with(marker) {
                itemName = place.name // ????????? ???????????? ??????
                userObject = place
                tag = 0
                mapPoint = MapPoint.mapPointWithGeoCoord(
                    place.latitude,
                    place.longitude
                ) // ?????? ??????
                markerType = MapPOIItem.MarkerType.BluePin // ???????????? ???????????? BluePin ?????? ??????.
                selectedMarkerType =
                    MapPOIItem.MarkerType.RedPin // ????????? ???????????????, ???????????? ???????????? RedPin ?????? ??????.
            }
            mapView.addPOIItem(marker)
        }
        val mapPoint = MapPoint.mapPointWithGeoCoord(placeInfo.first().latitude, placeInfo.first().longitude)
        mapView.setMapCenterPoint(mapPoint, true)
    }

    private fun setPolyLine(placeInfo: List<PlaceInfo>){
        val polyLine = MapPolyline()
        placeInfo.forEach{ place ->
            with(polyLine){
                tag = 1000
                lineColor = R.color.white_green
                addPoint(MapPoint.mapPointWithGeoCoord(place.latitude, place.longitude))
            }
            mapView.addPolyline(polyLine)
        }
        mapView.fitMapViewAreaToShowAllPolylines()
    }

    private fun sendKakaoLink(context: Context, defaultFeed: FeedTemplate) {
        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            ShareClient.instance.shareDefault(context, defaultFeed) { sharingResult, _ ->
                if (sharingResult != null) {
                    startActivity(sharingResult.intent)
                }
            }
        } else { // ??? ?????? ?????? ??????
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
    class CustomBalloonAdapter(private val inflater: LayoutInflater) : CalloutBalloonAdapter {

        private val itemCardBalloon = inflater.inflate(R.layout.item_card_balloon, null)
        private val placeName : TextView = itemCardBalloon.findViewById(R.id.item_balloon_title)
        private val placeAddress : TextView = itemCardBalloon.findViewById(R.id.item_balloon_address)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // ?????? ?????? ??? ????????? ?????????
            val item = poiItem?.userObject as PlaceInfo
            placeName.text = item.name
            placeAddress.text = item.roadAddress
            return itemCardBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // ????????? ?????? ???
            return itemCardBalloon
        }
    }
}
