package com.example.foroom.domain.usecase

import com.example.foroom.domain.model.Message
import com.example.foroom.domain.model.request.SendMessageRequest
import com.example.foroom.domain.repository.web_socket.ForoomMessagesWebSocketRepository
import com.example.shared.model.Result
import kotlinx.coroutines.flow.Flow

class MessageWebSocketUseCase(private val repository: ForoomMessagesWebSocketRepository) {

    fun connect(): Flow<Result<Unit>> = repository.connect()

    fun disconnect() = repository.disconnect()

    fun onMessageReceived(): Flow<Message> = repository.onMessageReceived()

    fun sendMessage(userId: String, chatId: Int, text: String): Flow<Result<Unit>> =
        repository.sendMessage(SendMessageRequest(userId, chatId, text))

    fun joinGroup(groupName: String): Flow<Result<Unit>> = repository.joinGroup(groupName)

    fun leaveGroup(groupName: String): Flow<Result<Unit>> = repository.leaveGroup(groupName)

}