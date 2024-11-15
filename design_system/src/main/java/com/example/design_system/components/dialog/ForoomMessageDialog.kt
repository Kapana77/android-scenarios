package com.example.design_system.components.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface.OnCancelListener
import android.os.Bundle
import com.example.design_system.databinding.LayoutForoomDialogBinding
import com.example.shared.extension.onClick

class ForoomMessageDialog private constructor(
    context: Context,
    private val message: String,
    cancellable: Boolean = true,
    onCancelListener: OnCancelListener? = null
) : Dialog(context, cancellable, onCancelListener) {
    private lateinit var binding: LayoutForoomDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutForoomDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.decorView?.setBackgroundResource(android.R.color.transparent)
        binding.messageTextView.text = message
        binding.closeTextView.onClick {
            cancel()
        }
    }

    companion object {
        fun showMessage(
            context: Context,
            message: String,
            onCancelListener: OnCancelListener? = null
        ) {
            ForoomMessageDialog(context, message, onCancelListener = onCancelListener).show()
        }
    }
}