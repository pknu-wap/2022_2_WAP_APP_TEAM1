package com.example.witt.presentation.ui.plan.drawup_plan

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.witt.R
import com.example.witt.data.api.KakaoAPI
import com.example.witt.data.model.search.Place
import com.example.witt.databinding.FragmentMapSearchBinding
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.data.model.search.ResultSearchKeyword
import com.example.witt.databinding.FragmentPlaceInsertBinding
import com.example.witt.presentation.ui.plan.drawup_plan.adapter.MapSearchAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.apache.commons.lang3.ObjectUtils.Null
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class PlaceInsertFragment: BaseFragment<FragmentPlaceInsertBinding>(R.layout.fragment_map_search){
    companion object{

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
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
}
