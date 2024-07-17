package com.example.foroom.presentation.ui.screens.log_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foroom.domain.model.LogInRequest
import com.example.foroom.domain.usecase.GetChatsUseCase
import com.example.foroom.domain.usecase.LogInUserUseCase
import com.example.network.rest_client.networkExecutor
import com.example.shared.model.Result
import com.example.shared.ui.viewModel.BaseViewModel

class ForoomLoginViewModel(private val logInUserUseCase: LogInUserUseCase) : BaseViewModel() {
    private val _logInLiveData = MutableLiveData<Result<Unit>>()
    val logInLiveData: LiveData<Result<Unit>> get() = _logInLiveData

    var userName: String = EMPTY_STRING
    var password: String = EMPTY_STRING

    fun logIn() {
        networkExecutor {
            execute {
                logInUserUseCase(LogInRequest(userName, password))
            }

            onResult {  result ->
                _logInLiveData.postValue(result)
            }
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}