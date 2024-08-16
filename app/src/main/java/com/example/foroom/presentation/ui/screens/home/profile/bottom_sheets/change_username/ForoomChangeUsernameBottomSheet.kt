package com.example.foroom.presentation.ui.screens.home.profile.bottom_sheets.change_username

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.design_system.components.bottom_sheet.ForoomActionBottomSheetFragment
import com.example.foroom.R
import com.example.foroom.databinding.LayoutChangeUsernameBottomSheetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomChangeUsernameBottomSheet :
    ForoomActionBottomSheetFragment<ForoomChangeUsernameViewModel, LayoutChangeUsernameBottomSheetBinding>() {

    override val inflateContent: (LayoutInflater, ViewGroup?, Boolean) -> LayoutChangeUsernameBottomSheetBinding
        get() = LayoutChangeUsernameBottomSheetBinding::inflate
    override val viewModel: ForoomChangeUsernameViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonText = getString(R.string.confirm)
    }
}