package com.example.foroom.presentation.ui.screens.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foroom.domain.model.Message
import com.example.foroom.domain.model.response.MessageHistoryResponse
import com.example.foroom.domain.usecase.GetMessageHistoryUseCase
import com.example.foroom.domain.usecase.MessageWebSocketUseCase
import com.example.foroom.presentation.ui.model.MessageUI
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

    private val _messagesLiveData = MutableLiveData<List<MessageUI>>()
    val messagesLiveData: LiveData<List<MessageUI>> get() = _messagesLiveData
    private val newMessages = mutableListOf<Message>()

    private val _connectionLiveData = MutableLiveData<Result<Unit>>()
    val connectionLiveData: LiveData<Result<Unit>> get() = _connectionLiveData

    var hasMoreMessages = false

    fun connect() {
        viewModelScope.launch {
            messagesUseCase.connect().collect { result ->
                if (result.isSuccess) {
                    getUserId()
                    joinGroup().collect { joinResult ->
                        _connectionLiveData.postValue(joinResult)
                    }
                } else {
                    _connectionLiveData.postValue(result)
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

            success { response ->
                paginationHelper.addPage(response.messages)
                hasMoreMessages = response.hasNext
                combineMessages()
            }
        }
    }

    private suspend fun onMessage() {
        messagesUseCase.onMessageReceived(userId).collect { message ->
            newMessages.add(FIRST_INDEX, message)
            combineMessages()
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

    private fun combineMessages() {
        val combined = newMessages + paginationHelper.getItems()

        _messagesLiveData.postValue(mapToMessageUI(combined))
    }

    private fun mapToMessageUI(messages: List<Message>): List<MessageUI> {
        return messages.mapIndexed { index, message ->
            with(message) {
                val isMerged = messages.getOrNull(index - 1)?.senderUserId == senderUserId

                MessageUI(
                    senderName,
                    senderAvatarUrl,
                    sendDate,
                    text,
                    senderUserId,
                    id,
                    isCurrentUser,
                    isMerged
                )
            }
        }
    }

    companion object {
        private const val FIRST_INDEX = 0
    }
}
