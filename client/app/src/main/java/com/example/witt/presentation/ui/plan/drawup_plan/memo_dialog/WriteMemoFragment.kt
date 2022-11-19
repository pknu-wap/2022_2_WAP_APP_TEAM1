package com.example.witt.presentation.ui.plan.drawup_plan.memo_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.witt.R
import com.example.witt.databinding.FragmentWriteMemoBinding
import com.example.witt.presentation.ui.plan.PlanViewModel
import com.example.witt.utils.dialogFragmentResize

class WriteMemoFragment: DialogFragment() {

    private var _binding: FragmentWriteMemoBinding? = null
    val binding get() = _binding ?: throw IllegalStateException()

    private val viewModel : WriteMemoViewModel by viewModels()
    private val planViewModel by activityViewModels<PlanViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_memo, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        initViews()
        observeData()

    }

    override fun onResume() {
        super.onResume()
        //dialog 가로 세로 비율 설정
        context?.dialogFragmentResize(this, width = 0.9f, height = 0.6f)
    }

    private fun initViews(){

        val memoText = arguments?.getString("memo") ?: ""
        arguments?.getInt("day")?.let{
            viewModel.setMemoInfo(memoText, it)
        } ?: dismiss() //날짜 없이 왔으면 dialog 종료

        binding.WriteMemoButton.setOnClickListener{
            viewModel.submitMemo()
            dismiss()
        }
        binding.cancelMemoButton.setOnClickListener{ dismiss() }

    }

    private fun observeData() {
        binding.viewModel = viewModel

        planViewModel.planState.observe(viewLifecycleOwner) {
            viewModel.setTripId(it.TripId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
