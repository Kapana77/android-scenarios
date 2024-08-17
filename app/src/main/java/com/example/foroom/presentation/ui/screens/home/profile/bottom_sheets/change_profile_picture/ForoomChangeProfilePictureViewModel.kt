package com.example.foroom.presentation.ui.screens.home.profile.bottom_sheets.change_profile_picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foroom.domain.usecase.ChangeUserAvatarUseCase
import com.example.foroom.domain.usecase.GetAvatarsUseCase
import com.example.network.rest_client.networkExecutor
import com.example.shared.model.Image
import com.example.shared.ui.viewModel.BaseViewModel
import com.example.shared.model.Result

class ForoomChangeProfilePictureViewModel(
    private val getAvatarsUseCase: GetAvatarsUseCase,
    private val changeUserAvatarUseCase: ChangeUserAvatarUseCase
) : BaseViewModel() {
    private val _avatarsLiveData = MutableLiveData<Result<List<Image>>>()
    val avatarsLiveData: LiveData<Result<List<Image>>> get() = _avatarsLiveData

    private val _changeUserAvatarLiveData = MutableLiveData<Result<Unit>>()
    val changeUserAvatarLiveData: LiveData<Result<Unit>> get() = _changeUserAvatarLiveData

    var selectedAvatarId = 0

    init {
        getAvatars()
    }

    private fun getAvatars() {
        networkExecutor {
            execute { getAvatarsUseCase() }

            onResult { result -> _avatarsLiveData.postValue(result) }
        }
    }

    fun changeUserAvatar() {
        networkExecutor {
            execute { changeUserAvatarUseCase(selectedAvatarId) }

            onResult { result -> _changeUserAvatarLiveData.postValue(result) }
        }
    }
}