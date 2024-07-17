package com.example.foroom.presentation.ui.screens.home.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.foroom.databinding.FragmentForoomProfileBinding
import com.example.shared.ui.fragment.BaseFragment

class ForoomProfileFragment: BaseFragment<ForoomProfileViewModel, FragmentForoomProfileBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForoomProfileBinding
        get() = FragmentForoomProfileBinding::inflate
    override val viewModel: ForoomProfileViewModel by viewModels()
}