package com.example.design_system.components.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.example.design_system.databinding.LayoutForoomActionBottomSheetBinding
import com.example.shared.ui.fragment.BaseBottomSheetFragment
import com.example.shared.ui.viewModel.BaseViewModel

abstract class ForoomActionBottomSheetFragment<VM : BaseViewModel, T : ViewBinding> :
    BaseBottomSheetFragment<VM, LayoutForoomActionBottomSheetBinding>() {

    final override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> LayoutForoomActionBottomSheetBinding =
        LayoutForoomActionBottomSheetBinding::inflate
    abstract val inflateContent: (LayoutInflater, ViewGroup?, Boolean) -> T

    var buttonText: String? = null
        set(value) {
            field = value
            binding.actionButton.text = value
        }

    var isButtonVisible: Boolean = true
        set(value) {
            field = value
            binding.actionButton.isVisible = value
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inflateContent(LayoutInflater.from(context), binding.contentContainer, true)
    }
}