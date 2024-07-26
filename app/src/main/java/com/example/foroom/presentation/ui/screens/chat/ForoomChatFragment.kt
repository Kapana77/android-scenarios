package com.example.foroom.presentation.ui.screens.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.foroom.databinding.FragmentForoomChatBinding
import com.example.shared.ui.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomChatFragment: BaseFragment<ForoomChatViewModel, FragmentForoomChatBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForoomChatBinding
        get() = FragmentForoomChatBinding::inflate
    override val viewModel: ForoomChatViewModel by viewModel()
}