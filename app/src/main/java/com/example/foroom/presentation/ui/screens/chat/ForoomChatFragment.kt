package com.example.foroom.presentation.ui.screens.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import com.example.design_system.R
import com.example.foroom.databinding.FragmentForoomChatBinding
import com.example.foroom.presentation.ui.model.ChatUI
import com.example.foroom.presentation.ui.screens.chat.adapter.ForoomMessagesAdapter
import com.example.navigation.guest.ForoomNavigationArgumentsHolder
import com.example.navigation.guest.navArgs
import com.example.navigation.util.navigationHost
import com.example.shared.extension.isError
import com.example.shared.extension.onClick
import com.example.shared.extension.onGlobalLayout
import com.example.shared.ui.fragment.BaseFragment
import kotlinx.coroutines.launch
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

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            initSocketConnection()
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

        viewModel.disConnect()
    }

    private suspend fun initSocketConnection() {
        viewModel.connect().collect { result ->
            view?.post {
                if (result.isError) navigationHost?.goBack()
            }
        }

        viewModel.joinGroup(navArgs.id).collect {
            Log.d("logkata", it.toString())
        }
    }

    private suspend fun collectMessages() {
        viewModel.onMessage().collect { message ->
            messagesAdapter.addMessage(message)
            binding.messagesRecyclerView.smoothScrollToPosition(0)
        }
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
    }

    private fun setListeners() {
        binding.sendMessageButton.onClick {
            lifecycleScope.launch {
                viewModel.sendMessage(navArgs.id, binding.messageInput.text)?.collect {
                    binding.messageInput.editText.text?.clear()
                }
            }
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