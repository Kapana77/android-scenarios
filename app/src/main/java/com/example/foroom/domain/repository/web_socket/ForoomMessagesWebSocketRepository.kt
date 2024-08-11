package com.example.foroom.domain.repository.web_socket

import com.example.foroom.domain.model.Message
import com.example.foroom.domain.model.request.SendMessageRequest
import com.example.shared.model.Result
import kotlinx.coroutines.flow.Flow

interface ForoomMessagesWebSocketRepository {

    fun connect(): Flow<Result<Unit>>

    fun disconnect()

    fun onMessageReceived(currentUserId: String): Flow<Message>

    fun sendMessage(sendMessageRequest: SendMessageRequest): Flow<Result<Unit>>

    fun joinGroup(groupName: String): Flow<Result<Unit>>

    fun leaveGroup(groupName: String): Flow<Result<Unit>>
}