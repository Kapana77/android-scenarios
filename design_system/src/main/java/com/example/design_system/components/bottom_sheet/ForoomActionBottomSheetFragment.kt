package com.example.design_system.components.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.example.design_system.databinding.LayoutForoomActionBottomSheetBinding
import com.example.shared.extension.onClick
import com.example.shared.ui.viewModel.BaseViewModel
import com.example.shared.util.events.ForoomEventsHubHolder
import com.example.shared.util.loading.GlobalLoadingDelegate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class ForoomActionBottomSheetFragment<VM : BaseViewModel, T : ViewBinding> :
    BottomSheetDialogFragment() {

    abstract val viewModel: VM

    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected val globalLoadingDelegate get() = activity as? GlobalLoadingDelegate

    protected val eventsHub get() = (activity as? ForoomEventsHubHolder)?.eventsHub

    private var baseBinding: LayoutForoomActionBottomSheetBinding? = null

    abstract val inflateContent: (LayoutInflater, ViewGroup?, Boolean) -> T

    var buttonText: String? = null
        set(value) {
            field = value
            baseBinding?.actionButton?.text = value
        }

    var isButtonVisible: Boolean = true
        set(value) {
            field = value
            baseBinding?.actionButton?.isVisible = value
        }

    open fun onActionButtonClick() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        baseBinding = LayoutForoomActionBottomSheetBinding.inflate(inflater, container, false)

        _binding = inflateContent(inflater, baseBinding?.contentContainer, true)

        return baseBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseBinding?.actionButton?.onClick {
            onActionButtonClick()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
        baseBinding = null
    }
}