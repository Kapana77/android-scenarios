package com.example.foroom.presentation.ui.screens.home.profile.bottom_sheets.change_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.design_system.components.bottom_sheet.ForoomActionBottomSheetFragment
import com.example.foroom.R
import com.example.foroom.databinding.LayoutChangePasswordBottomSheetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomChangePasswordBottomSheet :
    ForoomActionBottomSheetFragment<ForoomChangePasswordViewModel, LayoutChangePasswordBottomSheetBinding>() {

    override val inflateContent: (LayoutInflater, ViewGroup?, Boolean) -> LayoutChangePasswordBottomSheetBinding =
        LayoutChangePasswordBottomSheetBinding::inflate
    override val viewModel: ForoomChangePasswordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonText = getString(R.string.confirm)
    }
}