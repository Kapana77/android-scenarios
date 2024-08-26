package com.example.foroom.presentation.ui.screens.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.foroom.domain.model.User
import com.example.foroom.presentation.ui.delegate.saveuser.GetAndSaveUserDelegate
import com.example.foroom.presentation.ui.delegate.sign_out.SignOutDelegate
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.shared.extension.successValueOrNull
import com.example.shared.ui.viewModel.BaseViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ForoomProfileViewModel(
    private val userDataStore: ForoomUserDataStore,
    private val getAndSaveUserDelegate: GetAndSaveUserDelegate,
    private val signOutDelegate: SignOutDelegate
) : BaseViewModel(), GetAndSaveUserDelegate by getAndSaveUserDelegate,
    SignOutDelegate by signOutDelegate {
    private val _currentUserLiveData = MediatorLiveData<User>()
    val currentUserLiveData: LiveData<User> get() = _currentUserLiveData
    val remoteAvatarUrl get() = _currentUserLiveData.value?.avatarUrl

    init {
        getAndSaveUserDelegate.init(viewModelScope)
        signOutDelegate.init(viewModelScope)

        getCurrentUser()

        _currentUserLiveData.addSource(getAndSaveUserResultLiveData) { result ->
            result.successValueOrNull?.let { user ->
                _currentUserLiveData.value = user
            }
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            userDataStore.getUser().collect { user ->
                _currentUserLiveData.value = user
                cancel()
            }
        }
    }
}