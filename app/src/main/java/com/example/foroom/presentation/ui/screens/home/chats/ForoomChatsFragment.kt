package com.example.foroom.presentation.ui.screens.home.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foroom.databinding.FragmentForoomChatsBinding
import com.example.foroom.presentation.ui.screens.home.chats.adapter.ForoomChatsAdapter
import com.example.shared.ui.fragment.BaseFragment
import com.example.shared.ui.viewModel.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomChatsFragment : BaseFragment<ForoomChatsViewModel, FragmentForoomChatsBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForoomChatsBinding
        get() = FragmentForoomChatsBinding::inflate
    override val viewModel: ForoomChatsViewModel by viewModel()

    private val adapter by lazy {
        ForoomChatsAdapter {
            viewModel.getChats(BaseViewModel.RequestCode.RC_LOAD_MORE)
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