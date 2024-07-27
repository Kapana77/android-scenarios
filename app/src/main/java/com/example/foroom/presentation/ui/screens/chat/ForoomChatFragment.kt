package com.example.foroom.presentation.ui.screens.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import com.example.design_system.R
import com.example.foroom.databinding.FragmentForoomChatBinding
import com.example.foroom.domain.model.Message
import com.example.foroom.presentation.ui.model.ChatUI
import com.example.foroom.presentation.ui.screens.chat.adapter.ForoomMessagesAdapter
import com.example.navigation.guest.ForoomNavigationArgumentsHolder
import com.example.navigation.guest.navArgs
import com.example.navigation.util.navigationHost
import com.example.shared.extension.onGlobalLayout
import com.example.shared.ui.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime


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

        messagesAdapter.submitList(
            listOf(
                Message(1, "@Shio", LocalDateTime.now(), "ae kleebo ae", "", false),
                Message(2, "@Nia", LocalDateTime.now(), "ae kleebo ae", "", true),
                Message(3, "@Shio", LocalDateTime.now(), "ae kleebo ae", "", false),
//                Message(
//                    4,
//                    "@Nia",
//                    LocalDateTime.now(),
//                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
//                    "",
//                    true
//                ),
//                Message(
//                    5,
//                    "@Shio",
//                    LocalDateTime.now(),
//                    "ae kleebo aeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
//                    "",
//                    false
//                ),
//                Message(6, "@Nia", LocalDateTime.now(), "ae kleebo ae", "", true),
//                Message(7, "@Shio", LocalDateTime.now(), "ae kleebo ae", "", false),
//                Message(8, "@Nia", LocalDateTime.now(), "ae kleebo ae", "", true),
//                Message(9, "@Shio", LocalDateTime.now(), "ae kleebo ae", "", false),
//                Message(10, "@Nia", LocalDateTime.now(), "ae kleebo ae", "", true),
//                Message(11, "@Shio", LocalDateTime.now(), "ae kleebo ae", "", false),
//                Message(12, "@Nia", LocalDateTime.now(), "ae kleebo ae", "", true),
//                Message(13, "@Shio", LocalDateTime.now(), "ae kleebo ae", "", false),
//                Message(14, "@Nia", LocalDateTime.now(), "ae kleebo ae", "", true),
//                Message(15, "@Shio", LocalDateTime.now(), "ae kleebo ae", "", false),
//                Message(16, "@Nia", LocalDateTime.now(), "ae kleebo ae", "", true),
//                Message(17, "@Shio", LocalDateTime.now(), "ae kleebo ae", "", false),
//                Message(18, "@Nia", LocalDateTime.now(), "ae kleebo ae", "", true),
//                Message(19, "@Shio", LocalDateTime.now(), "ae kleebo ae", "", false),
//                Message(20, "@Nia", LocalDateTime.now(), "ae kleebo ae", "", true),
//                Message(21, "@Shio", LocalDateTime.now(), "ae kleebo ae", "", false),
//                Message(22, "@Nia", LocalDateTime.now(), "ae kleebo ae", "", true),
            )
        )
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