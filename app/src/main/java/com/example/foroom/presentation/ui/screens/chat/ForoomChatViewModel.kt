package com.example.foroom.presentation.ui.screens.chat

import com.example.foroom.domain.usecase.MessageWebSocketUseCase
import com.example.foroom.domain.usecase.UserInMemoryUseCase
import com.example.shared.ui.viewModel.BaseViewModel

class ForoomChatViewModel(
    private val messagesUseCase: MessageWebSocketUseCase,
    private val userInMemoryUseCase: UserInMemoryUseCase
) : BaseViewModel() {
    fun connect() = messagesUseCase.connect()

    fun disConnect() = messagesUseCase.disconnect()

    fun sendMessage(chatId: Int, text: String) =
        messagesUseCase.sendMessage("18bf48ab-f9c6-46ed-b3be-e5a0677b0082", chatId, text)

    fun onMessage() = messagesUseCase.onMessageReceived()

    fun joinGroup(chatId: Int) = messagesUseCase.joinGroup(chatId.toString())

    fun leaveGroup(chatId: Int) = messagesUseCase.leaveGroup(chatId.toString())
}