package com.example.foroom.presentation.ui.screens.home.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foroom.databinding.FragmentForoomHomeChatsBinding
import com.example.foroom.presentation.ui.screens.chat.ForoomChatFragment
import com.example.foroom.presentation.ui.screens.home.chats.adapter.ForoomChatsAdapter
import com.example.navigation.host.openNextPage
import com.example.navigation.util.navigationHost
import com.example.shared.ui.fragment.BaseFragment
import com.example.shared.ui.viewModel.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomHomeChatsFragment :
    BaseFragment<ForoomHomeChatsViewModel, FragmentForoomHomeChatsBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForoomHomeChatsBinding
        get() = FragmentForoomHomeChatsBinding::inflate
    override val viewModel: ForoomHomeChatsViewModel by viewModel()

    private val adapter by lazy {
        ForoomChatsAdapter {
            viewModel.getChats(BaseViewModel.RequestCode.RC_LOAD_MORE)
        }.apply {
            onSendButtonClicked = { chat ->
                navigationHost?.openNextPage(
                    ForoomChatFragment(),
                    args = chat,
                    animate = false
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
    }

    private fun initViews() {
        binding.chatsRecyclerView.adapter = adapter
    }

    private fun setObservers() {
        viewModel.chatsLiveData.handleResult {
            onSuccess { chats ->
                binding.root.showContent()
                adapter.submitDataList(chats, viewModel.hasMorePages)
            }

            onLoading {
                if (viewModel.requestCode.isInit()) binding.root.showLoader()
            }

            onError {
                if (viewModel.requestCode.isInit()) binding.root.showEmptyPage()
                else adapter.showErrorState()
            }
        }
    }
}