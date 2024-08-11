package com.example.foroom.data.repository.web_socket

import com.example.foroom.data.datasource.web_socket.MessagesWebSocketDataSource
import com.example.foroom.data.mapper.ForoomMapper
import com.example.foroom.domain.model.Message
import com.example.foroom.domain.model.request.SendMessageRequest
import com.example.foroom.domain.repository.web_socket.ForoomMessagesWebSocketRepository
import com.example.shared.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ForoomMessagesWebSocketRepositoryImpl(
    private val dataSource: MessagesWebSocketDataSource,
    private val foroomMapper: ForoomMapper
) : ForoomMessagesWebSocketRepository {
    override fun connect(): Flow<Result<Unit>> = dataSource.connect()

    override fun disconnect() = dataSource.disconnect()

    override fun onMessageReceived(currentUserId: String): Flow<Message> = dataSource.onMessageReceived().map { entity ->
        foroomMapper.mapToMessage(entity, currentUserId)
    }

    override fun sendMessage(sendMessageRequest: SendMessageRequest): Flow<Result<Unit>> =
        dataSource.sendMessage(foroomMapper.mapToSendMessageRequest(sendMessageRequest))

    override fun joinGroup(groupName: String): Flow<Result<Unit>> = dataSource.joinGroup(groupName)

    override fun leaveGroup(groupName: String): Flow<Result<Unit>> = dataSource.leaveGroup(groupName)
}