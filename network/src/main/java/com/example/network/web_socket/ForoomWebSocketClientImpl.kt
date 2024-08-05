package com.example.network.web_socket

import com.example.network.BuildConfig
import com.example.shared.model.Result
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ForoomWebSocketClientImpl(private val hub: ForoomHub) : ForoomWebSocketClient {

    private var _connection: HubConnection? = null
    private val connection: HubConnection get() = requireNotNull(_connection) {
        "You have to call connect() first..."
    }

    override fun connect(): Flow<Result<Unit>> {
        _connection = HubConnectionBuilder.create(BuildConfig.BASE_URL + hub.path).build()

        return callbackFlow {
            send(Result.Loading)

            val disposable = connection.start().subscribe({
                trySend(Result.Success(Unit))
                close()
            }, {
                trySend(Result.Error(Exception(it)))
                close()
            })

            awaitClose {
                disposable.dispose()
            }
        }
    }

    override fun disconnect() {
        _connection?.close()
        _connection = null
    }

    override fun <T> onReceived(dataClass: Class<T>): Flow<T> {
        return callbackFlow {
            val subscription = connection.on(
                RECEIVE_MESSAGE_FUNCTION,
                { trySend(it) },
                dataClass
            )

            awaitClose {
                subscription.unsubscribe()
            }
        }
    }

    override fun sendMessage(data: Any): Flow<Result<Unit>> {
        return callbackFlow {
            send(Result.Loading)

            val disposable = connection.invoke(SEND_MESSAGE_FUNCTION, data).subscribe({
                trySend(Result.Success(Unit))
                close()
            }, { error ->
                trySend(Result.Error(Exception(error)))
                close()
            })

            awaitClose {
                disposable.dispose()
            }
        }
    }

    companion object {
        private const val RECEIVE_MESSAGE_FUNCTION = "receiveMessage"
        private const val SEND_MESSAGE_FUNCTION = "SendMessage"
    }

    enum class ForoomHub(val path: String) {
        CHAT("Chat")
    }
}