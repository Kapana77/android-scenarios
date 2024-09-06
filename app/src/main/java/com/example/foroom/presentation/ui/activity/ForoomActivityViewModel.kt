package com.example.foroom.presentation.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foroom.domain.model.User
import com.example.foroom.domain.usecase.GetCurrentUserUseCase
import com.example.foroom.presentation.ui.delegate.saveuser.GetAndSaveUserDelegate
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.presentation.ui.util.exception.ForoomUnauthorizedUserException
import com.example.network.auth.UserTokenRuntimeHolder
import com.example.network.rest_client.networkExecutor
import com.example.shared.model.ForoomLanguage
import com.example.shared.model.Result
import com.example.shared.ui.viewModel.BaseViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ForoomActivityViewModel(
    private val userDataStore: ForoomUserDataStore,
    private val userTokenRuntimeHolder: UserTokenRuntimeHolder,
    getAndSaveUserDelegate: GetAndSaveUserDelegate
): BaseViewModel(), GetAndSaveUserDelegate by getAndSaveUserDelegate{
    private val _currentUserLiveData = MediatorLiveData<Result<User>>()
    val currentUserLiveData: LiveData<Result<User>> get() = _currentUserLiveData

    init {
        getAndSaveUserDelegate.init(viewModelScope)

        getCurrentUser()

        _currentUserLiveData.addSource(getAndSaveUserResultLiveData) { result ->
            _currentUserLiveData.value = result
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            userDataStore.getUserAuthToken().catch {
                _currentUserLiveData.postValue(Result.Error(ForoomUnauthorizedUserException()))
            }.collect { token ->
                userTokenRuntimeHolder.setUserToken(token)
                getAndSaveUserData()
                cancel()
            }
        }
    }

    suspend fun updateUserLanguage(language: ForoomLanguage) {
        userDataStore.saveUserLanguage(language.langName)
    }

    suspend fun getUserLanguage() = userDataStore.getUserLanguage()?.let { language ->
        ForoomLanguage.fromName(language)
    }
}