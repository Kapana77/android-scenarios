package com.example.foroom.presentation.ui.screens.home.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.foroom.R
import com.example.foroom.databinding.FragmentForoomHomeChatsBinding
import com.example.foroom.presentation.ui.screens.chat.ForoomChatFragment
import com.example.foroom.presentation.ui.screens.home.chats.adapter.ForoomChatsAdapter
import com.example.foroom.presentation.ui.screens.home.chats.events.ForoomHomeChatsEvents
import com.example.navigation.host.openNextPage
import com.example.navigation.util.navigationHost
import com.example.shared.extension.handleResult
import com.example.shared.ui.fragment.BaseFragment
import com.example.shared.ui.viewModel.BaseViewModel
import com.example.shared.util.events.observeEvent
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
        setListeners()
        setObservers()
    }

    private fun initViews() {
        binding.chatsRecyclerView.adapter = adapter

        with(binding.filterOptionsView) {
            addOption(getString(R.string.chats_filter_popular))
            addOption(getString(R.string.chats_filter_created))
            addOption(getString(R.string.chats_filter_favourite))
        }
    }

    private fun setListeners() {
        binding.filterOptionsView.onIndicated = { index ->
            when(index) {
                FILTER_INDEX_POPULAR -> viewModel.filterSearchByPopular()
                FILTER_INDEX_CREATED -> viewModel.filterSearchByCreated()
                FILTER_INDEX_FAVOURITE -> viewModel.filterSearchByFavorite()
            }
        }

        binding.searchChatInput.input.editText.addTextChangedListener { text ->
            viewModel.filterSearchByName(text?.toString())
        }
    }

    private fun setObservers() {
        eventsHub?.observeEvent<ForoomHomeChatsEvents.FilterCreatedChats>(viewLifecycleOwner) {
            binding.filterOptionsView.indicateAt(FILTER_INDEX_CREATED)
        }

        eventsHub?.observeEvent<ForoomHomeChatsEvents.FilterFavouriteChats>(viewLifecycleOwner) {
            binding.filterOptionsView.indicateAt(FILTER_INDEX_FAVOURITE)
        }

        eventsHub?.observeEvent<ForoomHomeChatsEvents.RefreshChats>(viewLifecycleOwner) {
            viewModel.getChats(BaseViewModel.RequestCode.RC_INIT)
        }

        viewModel.chatsLiveData.handleResult(viewLifecycleOwner) {
            onSuccess { chats ->
                binding.contentLoaderView.showContent()
                adapter.submitDataList(chats, viewModel.hasMorePages)
            }

            onLoading {
                if (viewModel.requestCode.isInit()) {
                    binding.contentLoaderView.showLoader()
                    adapter.clearData()
                }
            }

            onError {
                if (viewModel.requestCode.isInit()) binding.contentLoaderView.showEmptyPage()
                else adapter.showErrorState()
            }
        }
    }

    companion object {
        private const val FILTER_INDEX_POPULAR = 0
        private const val FILTER_INDEX_CREATED = 1
        private const val FILTER_INDEX_FAVOURITE = 2
    }
}