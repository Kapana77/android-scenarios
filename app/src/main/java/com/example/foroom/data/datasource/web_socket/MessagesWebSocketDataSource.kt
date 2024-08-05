package com.example.foroom.data.datasource.web_socket

import com.example.foroom.data.model.MessageEntity
import com.example.foroom.data.model.request.SendMessageRequestEntity
import com.example.shared.model.Result
import kotlinx.coroutines.flow.Flow

interface MessagesWebSocketDataSource {
    fun connect(): Flow<Result<Unit>>

    fun disconnect()

    fun onMessageReceived(): Flow<MessageEntity>

    fun sendMessage(sendMessageRequest: SendMessageRequestEntity): Flow<Result<Unit>>

    fun joinGroup(groupName: String): Flow<Result<Unit>>

    fun leaveGroup(groupName: String): Flow<Result<Unit>>
}