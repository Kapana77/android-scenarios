package com.example.foroom.presentation.ui.screens.home.chats

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foroom.domain.model.Chat
import com.example.foroom.domain.model.response.ChatsResponse
import com.example.foroom.domain.usecase.GetChatsUseCase
import com.example.foroom.domain.usecase.GetCurrentUserUseCase
import com.example.foroom.domain.usecase.UserInMemoryUseCase
import com.example.foroom.presentation.ui.model.ChatUI
import com.example.network.rest_client.networkExecutor
import com.example.shared.ui.viewModel.BaseViewModel
import com.example.shared.model.Result
import com.example.shared.util.pagination.PaginationHelper
import org.koin.core.component.get

class ForoomHomeChatsViewModel(
    private val getChatsUseCase: GetChatsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val userInMemoryUseCase: UserInMemoryUseCase
) : BaseViewModel() {
    private val _chatsLiveData = MutableLiveData<Result<List<ChatUI>>>()
    val chatsLiveData: LiveData<Result<List<ChatUI>>> get() = _chatsLiveData

    private val paginationHelper = get<PaginationHelper<ChatUI>>()

    private var searchConfiguration = SearchConfiguration()
        set(value) {
            field = value
            onSearchConfigurationChange()
        }


    var hasMorePages = true
        private set

    init {
        getChats(RequestCode.RC_INIT)
        updateCurrentUserInMemory()
    }

    fun getChats(requestCode: RequestCode) {
        this.requestCode = requestCode

        networkExecutor<ChatsResponse> {
            execute {
                getChatsUseCase(
                    paginationHelper.getPage(),
                    name = searchConfiguration.name,
                    popular = searchConfiguration.popular,
                    created = searchConfiguration.created,
                    favorite = searchConfiguration.favorite
                )
            }
            loading { _chatsLiveData.postValue(Result.Loading) }
            error { _chatsLiveData.postValue(Result.Error(it)) }

            success { chatsResponse ->
                hasMorePages = chatsResponse.hasNext
                paginationHelper.addPage(mapToChatUI(chatsResponse.chats))
                _chatsLiveData.postValue(Result.Success(paginationHelper.getItems()))
            }
        }
    }

    fun filterSearchByName(name: String?) {
        searchConfiguration = SearchConfiguration(name = name)
    }

    fun filterSearchByPopular() {
        searchConfiguration = SearchConfiguration(popular = true)
    }

    fun filterSearchByCreated() {
        searchConfiguration = SearchConfiguration(created = true)
    }

    fun filterSearchByFavorite() {
        searchConfiguration = SearchConfiguration(favorite = true)
    }

    private fun updateCurrentUserInMemory() {
        networkExecutor {
            execute { getCurrentUserUseCase() }
            success {  user ->
                userInMemoryUseCase.setCurrentUser(user)
            }
            error {
                // Todo handle error
                Log.d("logkata", it.message.toString())
            }
        }
    }

    private fun onSearchConfigurationChange() {
        paginationHelper.clear()
        getChats(RequestCode.RC_INIT)
    }

    private fun mapToChatUI(chats: List<Chat>) = chats.map { chat ->
        with(chat) {
            ChatUI(id, name, emojiUrl, creatorUsername, likeCount)
        }
    }

    private data class SearchConfiguration(
        val name: String? = null,
        val popular: Boolean = false,
        val created: Boolean = false,
        val favorite: Boolean = false
    )
}