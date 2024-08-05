package com.example.network.web_socket

import com.example.shared.model.Result
import kotlinx.coroutines.flow.Flow

interface ForoomWebSocketClient {
    fun connect(): Flow<Result<Unit>>

    fun disconnect()

    fun <T> onReceived(dataClass: Class<T>): Flow<T>

    fun sendMessage(data: Any): Flow<Result<Unit>>

    fun joinGroup(groupName: String): Flow<Result<Unit>>

    fun leaveGroup(groupName: String): Flow<Result<Unit>>
}