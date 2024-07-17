package com.example.foroom.presentation.ui.screens.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foroom.R
import com.example.foroom.domain.model.RegistrationRequest
import com.example.foroom.domain.usecase.GetAvatarsUseCase
import com.example.foroom.domain.usecase.RegisterUserUseCase
import com.example.network.rest_client.networkExecutor
import com.example.shared.model.Image
import com.example.shared.model.Result
import com.example.shared.ui.viewModel.BaseViewModel
import org.koin.core.component.KoinComponent

class ForoomRegistrationViewModel(
    private val getAvatarsUseCase: GetAvatarsUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : BaseViewModel(), KoinComponent {
    val avatarsLiveData = MutableLiveData<Result<List<Image>>>()

    private val _registrationLiveData = MutableLiveData<Result<Unit>>()
    val registrationLiveData: LiveData<Result<Unit>> get() = _registrationLiveData

    var userName: String = EMPTY_STRING
    var password: String = EMPTY_STRING
    var repeatedPassword: String = EMPTY_STRING

    var avatarId: Int = Image.BLANK_IMAGE_ID

    init {
        getImages()
    }

    private fun getImages() {
        networkExecutor {
            execute {
                getAvatarsUseCase()
            }

            onResult { result ->
                avatarsLiveData.postValue(result)
            }
        }
    }

    fun register() {
        if (avatarId == Image.BLANK_IMAGE_ID) {
            sendErrorMessage(R.string.blank_avatar_id_error)
            return
        }

        if (password != repeatedPassword) {
            sendErrorMessage(R.string.password_does_not_match_error)
            return
        }

        networkExecutor {
            execute {
                registerUserUseCase(RegistrationRequest(userName, password, avatarId))
            }

            onResult { result ->
                _registrationLiveData.postValue(result)
            }
        }
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}