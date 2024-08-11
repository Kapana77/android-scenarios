package com.example.foroom.presentation.ui.screens.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foroom.domain.model.Message
import com.example.foroom.domain.usecase.MessageWebSocketUseCase
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.shared.ui.viewModel.BaseViewModel
import kotlinx.coroutines.launch

class ForoomChatViewModel(
    private val messagesUseCase: MessageWebSocketUseCase,
    private val userDataStore: ForoomUserDataStore
) : BaseViewModel() {

    private var userId: String? = null

    private val _messagesLiveData = MutableLiveData<List<Message>>()
    val messagesLiveData: LiveData<List<Message>> get() = _messagesLiveData
    private val messages = mutableListOf<Message>()

    init {
        getUserId()
    }

    fun connect() = messagesUseCase.connect()

    fun disConnect() = messagesUseCase.disconnect()

    fun sendMessage(chatId: Int, text: String) = userId?.let { id ->
        messagesUseCase.sendMessage(userId = id, chatId, text)
    }

    private suspend fun onMessage(userId: String) {
        messagesUseCase.onMessageReceived(userId).collect { message ->
            messages.add(0, message)
            _messagesLiveData.postValue(messages.toList())
        }
    }

    fun joinGroup(chatId: Int) = messagesUseCase.joinGroup(chatId.toString())

    fun leaveGroup(chatId: Int) = messagesUseCase.leaveGroup(chatId.toString())

    private fun getUserId() {
        viewModelScope.launch {
            userDataStore.getUser().collect { user ->
                userId = user.id
                onMessage(user.id)
            }
        }
    }
}
