package com.example.witt.presentation.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.witt.databinding.DialogRemoveConfirmBinding

class RemoveConfirmDialog(
    context: Context,
    private val onClickRemove: () -> Unit = {},
    private val onClickCancel: () -> Unit = {}
) :Dialog(context){

    private val binding by lazy { DialogRemoveConfirmBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)

        binding.dialogRemoveButton.setOnClickListener{
            onClickRemove()
            dismiss()
        }

        binding.dialogCancelButton.setOnClickListener {
            onClickCancel()
            dismiss()
        }

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}