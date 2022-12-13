package com.example.witt.presentation.listener

import android.content.Context
import android.content.Intent
import android.net.Uri
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MarkerEventListener(val context: Context) : MapView.POIItemEventListener {
    override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
        // 마커 클릭 시
    }

    override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {
        // 말풍선 클릭 시 (Deprecated)
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        mapView: MapView?,
        poiItem: MapPOIItem?,
        buttonType: MapPOIItem.CalloutBalloonButtonType?
    ) {
        // 말풍선 클릭 시
        val intentKakaoMap =
            context.packageManager.getLaunchIntentForPackage("net.daum.android.map")
        try {
            val latitude = poiItem?.mapPoint?.mapPointGeoCoord?.latitude
            val longitude = poiItem?.mapPoint?.mapPointGeoCoord?.longitude
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("kakaomap://look?p=$latitude,$longitude"))
            context.startActivity(intent)
        } catch (e: Exception) {
            val intentPlayStore =
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$intentKakaoMap"))
            context.startActivity(intentPlayStore)
        }
    }

    override fun onDraggablePOIItemMoved(
        mapView: MapView?,
        poiItem: MapPOIItem?,
        mapPoint: MapPoint?
    ) {
        // 마커의 속성 중 isDraggable = true 일 때 마커를 이동시켰을 경우
    }
}