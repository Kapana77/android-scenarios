package com.example.foroom.presentation.ui.screens.home.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foroom.domain.model.Chat
import com.example.foroom.domain.usecase.GetChatsUseCase
import com.example.foroom.presentation.ui.util.adapter.ForoomLoadingListAdapter
import com.example.network.rest_client.networkExecutor
import com.example.shared.ui.viewModel.BaseViewModel
import com.example.shared.model.Result
import com.example.shared.util.pagination.PaginationHelper
import org.koin.core.component.get

class ForoomChatsViewModel(private val getChatsUseCase: GetChatsUseCase) : BaseViewModel() {
    private val _chatsLiveData = MutableLiveData<Result<List<Chat>>>()
    val chatsLiveData: LiveData<Result<List<Chat>>> get() = _chatsLiveData

    private val paginationHelper = get<PaginationHelper<Chat>>()

    var loadingListData: List<ForoomLoadingListAdapter.LoadingListItemType> = emptyList()
        private set

    val isInitializing get() = loadingListData.isEmpty()

    init {
        getChats()
    }

    fun getChats() {
        networkExecutor {
            execute { getChatsUseCase() }
            loading { _chatsLiveData.postValue(Result.Loading) }
            error { _chatsLiveData.postValue(Result.Error(it)) }

            success { chats ->
                paginationHelper.addPage(chats)
                loadingListData =
                    ForoomLoadingListAdapter.LoadingListItemType.fromData(paginationHelper.getItems())
                _chatsLiveData.postValue(Result.Success(chats))
            }
        }
    }
}