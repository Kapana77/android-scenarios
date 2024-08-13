package com.example.foroom.presentation.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foroom.domain.model.User
import com.example.foroom.domain.usecase.GetCurrentUserUseCase
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.presentation.ui.util.exception.ForoomUnauthorizedUserException
import com.example.network.auth.UserTokenRuntimeHolder
import com.example.network.rest_client.networkExecutor
import com.example.shared.model.Result
import com.example.shared.ui.viewModel.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ForoomActivityViewModel(
    private val userDataStore: ForoomUserDataStore,
    private val userTokenRuntimeHolder: UserTokenRuntimeHolder,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
): BaseViewModel() {
    private val _currentUserLiveData = MutableLiveData<Result<User>>()
    val currentUserLiveData: LiveData<Result<User>> get() = _currentUserLiveData

    init {
        getCurrentUser()
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            userDataStore.getUserAuthToken().catch {
                _currentUserLiveData.postValue(Result.Error(ForoomUnauthorizedUserException()))
            }.collect { token ->
                userTokenRuntimeHolder.setUserToken(token)
                fetchUserData()
            }
        }
    }

    private fun fetchUserData() {
        networkExecutor {
            execute { getCurrentUserUseCase() }

            onResult { result ->
                _currentUserLiveData.postValue(result)
            }
        }
    }

}