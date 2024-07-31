package com.example.network.web_socket

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder

class SignalrWebSocketClient(private val url: String) {

    private lateinit var connection: HubConnection

    fun connect() {
        connection = HubConnectionBuilder.create(url).build()
        connection.start()
    }

    fun disconnect() {
        connection.close()
    }

    fun onReceived(block: (String)-> Unit) {
        connection.on(
            "ReceiveMessage",
            block,
            String::class.java
        )
    }

    fun sendMessage(message: String) {
        connection.invoke("""
            {
                "chatId": 1,
                "userId": "asdsad",
                "message": "$message"
            }
        """.trimIndent(), message)
    }
}