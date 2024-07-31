package com.example.foroom.presentation.ui.screens.chat

import android.util.Log
import com.example.network.web_socket.SignalrWebSocketClient
import com.example.shared.ui.viewModel.BaseViewModel

class ForoomChatViewModel: BaseViewModel() {
    private val wsClient = SignalrWebSocketClient("ws://89.168.75.214/Chat")

    init {
        wsClient.connect()
        wsClient.onReceived {
            Log.d("logkata", it)
        }
    }

    fun sendMessage(message: String) {
        wsClient.sendMessage(message)
    }
}