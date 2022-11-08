package com.example.witt.presentation.ui.plan.drawup_plan

import android.content.ActivityNotFoundException
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.witt.R
import com.example.witt.databinding.FragmentDrawUpPlanBinding
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.presentation.ui.plan.drawup_plan.adapter.DatePlanAdapter
import com.example.witt.presentation.ui.plan.drawup_plan.adapter.TimePlanAdapter
import com.example.witt.presentation.ui.plan.drawup_plan.example.PlanDummy
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
    private lateinit var mapView: MapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initButton()
        initAdapter()
        //initMap()
    }

    private fun initView(){
        binding.dateTextView.text = "2022.11.21 ~ 2022.11.23"
        binding.destinationTextView.text = "부산/경상"
        binding.planNameTextView.text = "성훈쿤의 생일기념 여행"
    }

    private fun initButton(){
        binding.sharePlanButton.setOnClickListener {
            sendKakaoLink(requireContext(), DefaultTemplate.kakaoTemplate)
        }
    }

    private fun initAdapter(){
        //시간순 어댑터
        timePlanAdapter = TimePlanAdapter(
            memoClick = {
                showMemoDialog(it.memo)
            }
        )

        timePlanAdapter.submitList(PlanDummy.getTimePlan())


        //날짜 어댑터
        datePlanAdapter = DatePlanAdapter(
            context = requireContext(),
            timePlanAdapter = timePlanAdapter,
            memoButtonClick = {
                showMemoDialog(null)
            },
            placeButtonClick = {
               //todo place 작성페이지 전환
            }
        )
        datePlanAdapter.submitList(PlanDummy.getDatePlan())
        binding.datePlanRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.datePlanRecyclerView.adapter = datePlanAdapter

    }

    private fun showMemoDialog(memo: String?){
        val memoDialog = WriteMemoFragment()
        memo?.let{
            val args = Bundle()
            args.putString("memo", it)
            memoDialog.arguments = args
        }
        memoDialog.show(requireActivity().supportFragmentManager, "MEMO")
    }


    private fun initMap(){
        mapView = MapView(requireActivity())
        binding.mapView.addView(mapView)
    }

    private fun sendKakaoLink(context: Context, defaultFeed: FeedTemplate) {
        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            // 카카오톡으로 카카오톡 공유 가능
            ShareClient.instance.shareDefault(context, defaultFeed) { sharingResult, error ->
               if (sharingResult != null) {
                    Log.d("tag", "카카오톡 공유 성공 ${sharingResult.intent}")
                    startActivity(sharingResult.intent)

                    // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.w("tag", "Warning Msg: ${sharingResult.warningMsg}")
                    Log.w("tag", "Argument Msg: ${sharingResult.argumentMsg}")
                }
            }
        } else {
            // 웹 공유 예시 코드
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)

            try {
                KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
            } catch (e: UnsupportedOperationException) {
                //todo CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
            }

            try {
                KakaoCustomTabsClient.open(context, sharerUrl)
            } catch (e: ActivityNotFoundException) {
                //todo 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
            }
        }
    }
}