package com.example.witt.presentation.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.witt.databinding.DialogJoinPlanBinding

class JoinPlanDialog(
    context: Context,
    private val tripName: String,
    private val tripDate: String,
    private val onClickJoin: () -> Unit = {},
    private val onClickCancel: () -> Unit = {}
) : Dialog(context){

    private val binding by lazy { DialogJoinPlanBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)

        binding.dialogJoinButton.setOnClickListener{
            onClickJoin()
            dismiss()
        }

        binding.dialogJoinPlanCancelButton.setOnClickListener {
            onClickCancel()
            dismiss()
        }

        binding.dialogJoinPlanTextView.text = tripName + "여행에 초대되었습니다!"
        binding.dialogJoinPlanTextView2.text = tripDate

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}