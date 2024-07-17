package com.example.foroom.presentation.ui.screens.home.create_chat

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.foroom.databinding.FragmentForoomCreateChatBinding
import com.example.shared.ui.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomCreateChatFragment: BaseFragment<ForoomCreateChatViewModel, FragmentForoomCreateChatBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForoomCreateChatBinding
        get() = FragmentForoomCreateChatBinding::inflate
    override val viewModel: ForoomCreateChatViewModel by viewModel()
}