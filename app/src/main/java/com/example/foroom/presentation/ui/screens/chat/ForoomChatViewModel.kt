package com.example.foroom.presentation.ui.screens.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foroom.domain.model.Message
import com.example.foroom.domain.model.response.MessageHistoryResponse
import com.example.foroom.domain.usecase.GetMessageHistoryUseCase
import com.example.foroom.domain.usecase.MessageWebSocketUseCase
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.network.rest_client.networkExecutor
import com.example.shared.extension.isSuccess
import com.example.shared.extension.orEmpty
import com.example.shared.model.Result
import com.example.shared.ui.viewModel.BaseViewModel
import com.example.shared.util.pagination.PaginationHelper
import kotlinx.coroutines.launch

class ForoomChatViewModel(
    private val chatId: Int,
    private val messagesUseCase: MessageWebSocketUseCase,
    private val userDataStore: ForoomUserDataStore,
    private val getMessageHistoryUseCase: GetMessageHistoryUseCase,
    private val paginationHelper: PaginationHelper<Message>
) : BaseViewModel() {

    private lateinit var userId: String

    private val _messagesLiveData = MutableLiveData<List<Message>>()
    val messagesLiveData: LiveData<List<Message>> get() = _messagesLiveData
    private val messages = mutableListOf<Message>()

    private val _messageHistoryLiveData = MutableLiveData<Result<List<Message>>>()
    val messageHistoryLiveData: LiveData<Result<List<Message>>> get() = _messageHistoryLiveData

    private val _connectionLiveData = MutableLiveData<Result<Unit>>()
    val connectionLiveData: LiveData<Result<Unit>> get() = _connectionLiveData

    var hasMoreMessages = true

    fun connect() {
        viewModelScope.launch {
            messagesUseCase.connect().collect { result ->
                _connectionLiveData.postValue(result)

                if (result.isSuccess) {
                    getUserId()
                }
            }
            messagesUseCase.joinGroup(chatId.toString())
        }
    }

    fun disConnect() = messagesUseCase.disconnect()

    fun sendMessage(text: String) = messagesUseCase.sendMessage(userId = userId, chatId, text)

    fun joinGroup() = messagesUseCase.joinGroup(chatId.toString())

    fun leaveGroup() = messagesUseCase.leaveGroup(chatId.toString())

    fun getMessageHistory() {
        networkExecutor<MessageHistoryResponse> {
            execute {
                getMessageHistoryUseCase(
                    userId.orEmpty(),
                    chatId,
                    paginationHelper.getPage()
                )
            }

            loading {
                _messageHistoryLiveData.postValue(Result.Loading)
            }

            error { error ->
                _messageHistoryLiveData.postValue(Result.Error(error))
            }

            success { response ->
                paginationHelper.addPage(response.messages)
                hasMoreMessages = response.hasNext
                _messageHistoryLiveData.postValue(Result.Success(paginationHelper.getItems()))
            }
        }
    }

    private suspend fun onMessage() {
        messagesUseCase.onMessageReceived(userId).collect { message ->
            messages.add(FIRST_INDEX, message)
            _messagesLiveData.postValue(messages.toList())
        }
    }

    private fun getUserId() {
        viewModelScope.launch {
            userDataStore.getUser().collect { user ->
                userId = user.id
                getMessageHistory()
                onMessage()
            }
        }
    }

    companion object {
        private const val FIRST_INDEX = 0
    }
}
