package com.example.foroom.presentation.ui.screens.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foroom.domain.model.User
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.shared.ui.viewModel.BaseViewModel
import kotlinx.coroutines.launch

class ForoomProfileViewModel(
    private val userDataStore: ForoomUserDataStore
): BaseViewModel() {
    private val _currentUserLiveData = MutableLiveData<User>()
    val currentUserLiveData: LiveData<User> get() = _currentUserLiveData

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            userDataStore.getUser().collect { user ->
                _currentUserLiveData.value = user
            }
        }
    }
}