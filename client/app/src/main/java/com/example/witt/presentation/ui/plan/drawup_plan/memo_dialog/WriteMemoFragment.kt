package com.example.witt.presentation.ui.plan.drawup_plan.memo_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.witt.R
import com.example.witt.databinding.FragmentWriteMemoBinding
import com.example.witt.utils.dialogFragmentResize

class WriteMemoFragment: DialogFragment() {

    private var _binding: FragmentWriteMemoBinding? = null
    val binding get() = _binding ?: throw IllegalStateException()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_memo, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        //memo data mapping
        arguments?.getString("memo")?.let{
            binding.memoEditTextView.setText(it)
        }

        binding.WriteMemoButton.setOnClickListener{
            dismiss()
        }
        binding.cancelMemoButton.setOnClickListener{
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        //dialog 가로 세로 비율 설정
        context?.dialogFragmentResize(this, width = 0.9f, height = 0.6f)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
