package com.example.foroom.presentation.ui.screens.create_chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shared.model.Image
import com.example.foroom.domain.usecase.GetEmojisUseCase
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.network.rest_client.networkExecutor
import com.example.shared.ui.viewModel.BaseViewModel
import com.example.shared.model.Result
import kotlinx.coroutines.launch

class ForoomCreateChatViewModel(
    private val getEmojisUseCase: GetEmojisUseCase,
    private val userDataStore: ForoomUserDataStore
): BaseViewModel() {
    private val _emojisLiveData = MutableLiveData<Result<List<Image>>>()
    val emojisLiveData: LiveData<Result<List<Image>>> get() = _emojisLiveData

    private val _currentUserNameLiveData = MutableLiveData<String>()
    val currentUserNameLiveData: LiveData<String> get() = _currentUserNameLiveData

    init {
        getEmojis()
        getCurrentUserName()
    }

    private fun getEmojis() {
        networkExecutor {
            execute { getEmojisUseCase() }

            onResult { result ->
                _emojisLiveData.postValue(result)
            }
        }
    }

    private fun getCurrentUserName() {
        viewModelScope.launch {
            userDataStore.getUser().collect { user ->
                _currentUserNameLiveData.postValue(user.userName)
            }
        }
    }
}