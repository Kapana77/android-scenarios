package com.example.foroom.presentation.ui.screens.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import com.example.design_system.R
import com.example.foroom.databinding.FragmentForoomChatBinding
import com.example.foroom.presentation.ui.model.ChatUI
import com.example.foroom.presentation.ui.screens.chat.adapter.ForoomMessagesAdapter
import com.example.navigation.guest.ForoomNavigationArgumentsHolder
import com.example.navigation.guest.navArgs
import com.example.navigation.util.navigationHost
import com.example.shared.extension.onGlobalLayout
import com.example.shared.ui.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ForoomChatFragment : BaseFragment<ForoomChatViewModel, FragmentForoomChatBinding>(),
    ForoomNavigationArgumentsHolder<ChatUI> {
    override val bundle: Bundle? get() = arguments
    override val argClass: Class<ChatUI> = ChatUI::class.java

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForoomChatBinding
        get() = FragmentForoomChatBinding::inflate
    override val viewModel: ForoomChatViewModel by viewModel()

    private val messagesAdapter by lazy {
        ForoomMessagesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        initMessagesRecyclerView()

        with(binding.chatHeaderView) {
            setChatTitle(navArgs.name)
            setAuthorName(navArgs.creatorUsername)
            setChatImageUrl(navArgs.emojiUrl)

            setOnCloseButtonClickListener {
                navigationHost?.goBack()
            }
        }

        binding.chatHeaderView.setOnClickListener {
            viewModel.sendMessage("asdasd")
        }
    }

    private fun initMessagesRecyclerView() {
        binding.chatHeaderView.onGlobalLayout {
            binding.messagesRecyclerView.updatePadding(
                top = bottom + resources.getDimensionPixelSize(R.dimen.spacing_16)
            )

            binding.messagesRecyclerView.adapter = messagesAdapter
        }
    }
}