package com.example.foroom.presentation.ui.delegate.saveuser

import androidx.lifecycle.LiveData
import com.example.foroom.domain.model.User
import com.example.shared.model.Result
import com.example.shared.ui.delegate.ForoomDelegate

interface GetAndSaveUserDelegate: ForoomDelegate {
    val getAndSaveUserResultLiveData: LiveData<Result<User>>

    fun getAndSaveUserData()
}