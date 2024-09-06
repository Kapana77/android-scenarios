package com.example.foroom.presentation.ui.screens.home.profile.bottom_sheets.change_language

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foroom.domain.usecase.ChangePasswordUseCase
import com.example.network.rest_client.networkExecutor
import com.example.shared.model.Result
import com.example.shared.ui.viewModel.BaseViewModel

class ForoomChangeLanguageViewModel(
    private val changePasswordUseCase: ChangePasswordUseCase
): BaseViewModel() {
    private val _changePasswordLiveData = MutableLiveData<Result<Unit>>()
    val changePasswordLiveData: LiveData<Result<Unit>> get() = _changePasswordLiveData

    fun changePassword(newPassword: String) {
        networkExecutor {
            execute { changePasswordUseCase(newPassword) }

            onResult { result -> _changePasswordLiveData.postValue(result) }
        }
    }
}