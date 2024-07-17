package com.example.foroom.presentation.ui.screens.home.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.foroom.databinding.FragmentForoomChatsBinding
import com.example.foroom.presentation.ui.screens.home.chats.adapter.ForoomChatsAdapter
import com.example.shared.ui.fragment.BaseFragment
import com.example.shared.util.recyclerview.RecyclerViewBottomReachListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomChatsFragment : BaseFragment<ForoomChatsViewModel, FragmentForoomChatsBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForoomChatsBinding
        get() = FragmentForoomChatsBinding::inflate
    override val viewModel: ForoomChatsViewModel by viewModel()

    private val adapter by lazy {
        ForoomChatsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservers()
    }

    private fun initViews() {
        binding.chatsRecyclerView.adapter = adapter
        binding.chatsRecyclerView.addOnScrollListener(RecyclerViewBottomReachListener {
            viewModel.getChats()
        })
    }

    private fun setObservers() {
        viewModel.chatsLiveData.handleResult {
            onSuccess {
                binding.root.showContent()
                adapter.submitList(viewModel.loadingListData)
            }

            onLoading {
                if (viewModel.isInitializing) binding.root.showLoader()
            }

            onError {
                if (viewModel.isInitializing) binding.root.showEmptyPage()
                else adapter.showErrorState()
            }
        }
    }
}