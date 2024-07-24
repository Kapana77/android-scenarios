package com.example.foroom.presentation.ui.screens.home.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foroom.domain.model.Chat
import com.example.foroom.domain.model.ChatsResponse
import com.example.foroom.domain.usecase.GetChatsUseCase
import com.example.network.rest_client.networkExecutor
import com.example.shared.ui.viewModel.BaseViewModel
import com.example.shared.model.Result
import com.example.shared.util.pagination.PaginationHelper
import org.koin.core.component.get

class ForoomChatsViewModel(private val getChatsUseCase: GetChatsUseCase) : BaseViewModel() {
    private val _chatsLiveData = MutableLiveData<Result<List<Chat>>>()
    val chatsLiveData: LiveData<Result<List<Chat>>> get() = _chatsLiveData

    private val paginationHelper = get<PaginationHelper<Chat>>()

    var hasMorePages = true
        private set

    init {
        getChats(RequestCode.RC_INIT)
    }

    fun getChats(requestCode: RequestCode) {
        this.requestCode = requestCode

        networkExecutor<ChatsResponse> {
            execute { getChatsUseCase(paginationHelper.getPage()) }
            loading { _chatsLiveData.postValue(Result.Loading) }
            error { _chatsLiveData.postValue(Result.Error(it)) }

            success { chatsResponse ->
                hasMorePages = chatsResponse.hasNext
                paginationHelper.addPage(chatsResponse.chats)
                _chatsLiveData.postValue(Result.Success(paginationHelper.getItems()))
            }
        }
    }
}