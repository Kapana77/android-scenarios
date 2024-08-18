package com.example.foroom.presentation.ui.screens.home.profile.bottom_sheets.change_username

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foroom.domain.usecase.ChangeUsernameUseCase
import com.example.network.rest_client.networkExecutor
import com.example.shared.model.Result
import com.example.shared.ui.viewModel.BaseViewModel

class ForoomChangeUsernameViewModel(
    private val changeUsernameUseCase: ChangeUsernameUseCase
): BaseViewModel() {

    private val _changeUsernameLiveData = MutableLiveData<Result<Unit>>()
    val changeUsernameLiveData: LiveData<Result<Unit>> get() = _changeUsernameLiveData

    fun changeUsername(newUsername: String) {
        networkExecutor {
            execute { changeUsernameUseCase(newUsername) }

            onResult { result ->
                _changeUsernameLiveData.postValue(result)
            }
        }
    }
}