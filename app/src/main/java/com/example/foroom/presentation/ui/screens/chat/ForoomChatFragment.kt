package com.example.foroom.presentation.ui.screens.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import com.example.foroom.R
import com.example.design_system.components.dialog.ForoomMessageDialog
import com.example.foroom.databinding.FragmentForoomChatBinding
import com.example.foroom.presentation.ui.model.ChatUI
import com.example.foroom.presentation.ui.screens.chat.adapter.ForoomMessagesAdapter
import com.example.navigation.guest.ForoomNavigationArgumentsHolder
import com.example.navigation.guest.requireNavArgs
import com.example.navigation.util.navigationHost
import com.example.shared.extension.isError
import com.example.shared.extension.onClick
import com.example.shared.extension.onGlobalLayout
import com.example.shared.extension.resetSoftInputMode
import com.example.shared.extension.setSoftInputModeResize
import com.example.shared.ui.fragment.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ForoomChatFragment : BaseFragment<ForoomChatViewModel, FragmentForoomChatBinding>(),
    ForoomNavigationArgumentsHolder<ChatUI> {
    override val bundle: Bundle? get() = arguments
    override val argClass: Class<ChatUI> = ChatUI::class.java

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForoomChatBinding
        get() = FragmentForoomChatBinding::inflate
    override val viewModel: ForoomChatViewModel by viewModel {
        parametersOf(requireNavArgs.id)
    }

    private val messagesAdapter by lazy {
        ForoomMessagesAdapter(
            onLoadMore = {
                viewModel.getMessageHistory()
            },
            onErrorRefresh = {
                viewModel.getMessageHistory()
            }
        )
    }

    override fun onStart() {
        super.onStart()

        viewModel.connect()
        observeConnection()
        lifecycleScope.launch {
            collectMessages()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setListeners()
    }

    override fun onStop() {
        super.onStop()

        viewModel.leaveGroup()
        viewModel.disConnect()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        resetSoftInputMode()
    }

    private fun observeConnection() {
        viewModel.connectionLiveData.observe(viewLifecycleOwner) { result ->
            if (result.isError) {
                ForoomMessageDialog.showMessage(
                    requireContext(),
                    getString(R.string.error_could_not_connect_to_chat)
                ) {
                    navigationHost?.goBack()
                }
            }
        }
    }

    private fun collectMessages() {
        viewModel.messagesLiveData.observe(viewLifecycleOwner) { messages ->
            val shouldScrollToBottom = !binding.messagesRecyclerView.canScrollVertically(1)

            messagesAdapter.submitDataList(messages, viewModel.hasMoreMessages)
            if (shouldScrollToBottom) binding.messagesRecyclerView.smoothScrollToPosition(0)
        }
    }

    private fun initViews() {
        setSoftInputModeResize()

        initMessagesRecyclerView()

        with(binding.chatHeaderView) {
            setChatTitle(requireNavArgs.name)
            setAuthorName(requireNavArgs.creatorUsername)
            setChatImageUrl(requireNavArgs.emojiUrl)
        }

        binding.closeButton.onClick {
            navigationHost?.goBack()
        }
    }

    private fun setListeners() {
        binding.sendMessageButton.onClick {
            lifecycleScope.launch {
                viewModel.sendMessage(binding.messageInput.text).collect {
                    binding.messageInput.editText.text?.clear()
                    binding.messagesRecyclerView.smoothScrollToPosition(0)
                }
            }
        }
    }

    private fun initMessagesRecyclerView() {
        binding.chatHeaderView.onGlobalLayout {
            binding.messagesRecyclerView.updatePadding(
                top = bottom + resources.getDimensionPixelSize(com.example.design_system.R.dimen.spacing_16)
            )

            binding.messagesRecyclerView.adapter = messagesAdapter
        }
    }
}