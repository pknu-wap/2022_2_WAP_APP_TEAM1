package com.example.witt.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.witt.R
import com.example.witt.data.model.search.PlaceModel
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
        val place: PlaceModel = args.place
        val xPosition = place.x.toDouble()
        val yPosition = place.y.toDouble()

        //initMapView
        binding.mapView.addView(mapView)
        with(mapView){
            setMapCenterPoint(MapPoint.mapPointWithGeoCoord(yPosition, xPosition), true)
            setZoomLevel(1, true)
        }

        //init Marker
        val marker = MapPOIItem()
        with(marker){
            itemName = place.place_name
            tag = 0
            mapPoint = MapPoint.mapPointWithGeoCoord(yPosition, xPosition)
            markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
            selectedMarkerType = MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때 RedPin 마커 모양.
        }
        mapView.addPOIItem(marker)
    }
}