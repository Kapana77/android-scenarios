package com.example.foroom.presentation.ui.screens.log_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foroom.domain.model.request.LogInRequest
import com.example.foroom.domain.usecase.LogInUserUseCase
import com.example.foroom.presentation.ui.delegate.saveuser.GetAndSaveUserDelegate
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.network.auth.UserTokenRuntimeHolder
import com.example.network.model.response.UserTokenResponse
import com.example.network.rest_client.networkExecutor
import com.example.shared.model.Result
import com.example.shared.ui.viewModel.BaseViewModel
import kotlinx.coroutines.launch

class ForoomLoginViewModel(
    private val logInUserUseCase: LogInUserUseCase,
    private val userTokenRuntimeHolder: UserTokenRuntimeHolder,
    private val getAndSaveUserDelegate: GetAndSaveUserDelegate,
    private val userDataStore: ForoomUserDataStore
) : BaseViewModel(), GetAndSaveUserDelegate by getAndSaveUserDelegate {
    private val _logInLiveData = MutableLiveData<Result<UserTokenResponse>>()
    val logInLiveData: LiveData<Result<UserTokenResponse>> get() = _logInLiveData

    var userName: String = EMPTY_STRING
    var password: String = EMPTY_STRING

    init {
        getAndSaveUserDelegate.init(viewModelScope)
    }

    fun logIn() {
        networkExecutor<UserTokenResponse> {
            execute {
                logInUserUseCase(LogInRequest(userName, password))
            }

            onResult {  result ->
                _logInLiveData.postValue(result)
            }

            success { response ->
                userTokenRuntimeHolder.setUserToken(response.token)
                getAndSaveUserData()

                viewModelScope.launch {
                    userDataStore.saveUserAuthToken(response.token)
                }
            }
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}