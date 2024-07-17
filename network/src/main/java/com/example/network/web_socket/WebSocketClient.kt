package com.example.network.web_socket

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocketListener

class WebSocketClient(private val socketUrl: String) {
    private lateinit var webSocket: okhttp3.WebSocket
    private var socketListener: SocketListener? = null
    private var shouldReconnect = true
    private var client: OkHttpClient? = null

    private val webSocketListener = object : WebSocketListener() {
        override fun onMessage(webSocket: okhttp3.WebSocket, text: String) {
            socketListener?.onMessage(text)
        }

        override fun onClosed(webSocket: okhttp3.WebSocket, code: Int, reason: String) {
            if (shouldReconnect) reconnect()
        }

        override fun onFailure(webSocket: okhttp3.WebSocket, t: Throwable, response: Response?) {
            Log.d("Logkata", "onFailure -----> ${t.message} ")
            if (shouldReconnect) reconnect()
        }
    }

    fun setListener(listener: SocketListener) {
        this.socketListener = listener
    }

    fun connect() {
        shouldReconnect = true
        initWebSocket()
    }

    fun reconnect() {
        initWebSocket()
    }

    fun sendMessage(message: String) {
        if (::webSocket.isInitialized) webSocket.send(message)
    }

    fun disconnect() {
        if (::webSocket.isInitialized) webSocket.close(1000, "Do not need connection anymore.")
        shouldReconnect = false
    }

    private fun initWebSocket() {
        client = OkHttpClient()
        val request = Request.Builder().url(url = socketUrl).build()
        webSocket = client!!.newWebSocket(request, webSocketListener)

        //this must me done else memory leak will be caused
        client?.dispatcher?.executorService?.shutdown()
    }

    fun interface SocketListener {
        fun onMessage(message: String)
    }

    companion object {
        private lateinit var instance: WebSocketClient
        @JvmStatic
        @Synchronized
        //This function gives singleton instance of WebSocket.
        fun getInstance(socketUrl: String): WebSocketClient {
            synchronized(WebSocketClient::class) {
                if (!Companion::instance.isInitialized) {
                    instance = WebSocketClient(socketUrl)
                }
            }
            return instance
        }
    }
}
