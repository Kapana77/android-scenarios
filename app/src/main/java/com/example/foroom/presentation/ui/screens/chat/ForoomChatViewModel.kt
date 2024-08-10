package com.example.foroom.presentation.ui.screens.chat

import androidx.lifecycle.viewModelScope
import com.example.foroom.domain.usecase.MessageWebSocketUseCase
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.shared.ui.viewModel.BaseViewModel
import kotlinx.coroutines.launch

class ForoomChatViewModel(
    private val messagesUseCase: MessageWebSocketUseCase,
    private val userDataStore: ForoomUserDataStore
) : BaseViewModel() {

    private var userId: String? = null

    init {
        getUserId()
    }

    fun connect() = messagesUseCase.connect()

    fun disConnect() = messagesUseCase.disconnect()

    fun sendMessage(chatId: Int, text: String) = userId?.let { id ->
        messagesUseCase.sendMessage(userId = id, chatId, text)
    }


    fun onMessage() = messagesUseCase.onMessageReceived()

    fun joinGroup(chatId: Int) = messagesUseCase.joinGroup(chatId.toString())

    fun leaveGroup(chatId: Int) = messagesUseCase.leaveGroup(chatId.toString())

    private fun getUserId() {
        viewModelScope.launch {
            userDataStore.getUser().collect { user ->
                userId = user.id
            }
        }
    }
}
