package com.example.witt.presentation.ui.plan.drawup_plan

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.witt.BuildConfig
import com.example.witt.R
import com.example.witt.data.model.search.Place
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.databinding.FragmentPlaceInsertBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class PlaceInsertFragment: BaseFragment<FragmentPlaceInsertBinding>(R.layout.fragment_place_insert){

    private val args: PlaceInsertFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
    }

    private fun initMap() {
        val mapView by lazy { MapView(requireContext()) }
        val place: Place = args.place
        binding.mapView.addView(mapView)
        val xPosition = place.x.toDouble()
        val yPosition = place.y.toDouble()
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(yPosition, xPosition), true)
        mapView.setZoomLevel(1, true)
        val marker = MapPOIItem()
        marker.itemName = place.place_name
        marker.tag = 0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(yPosition, xPosition)
        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.

        marker.selectedMarkerType =
            MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker)
    }
}