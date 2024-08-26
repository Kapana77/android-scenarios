package com.example.foroom.presentation.ui.delegate.sign_out

import androidx.lifecycle.LiveData
import com.example.shared.ui.delegate.ForoomDelegate
import com.example.shared.model.Result

interface SignOutDelegate: ForoomDelegate {
    val signOutLiveData: LiveData<Result<Unit>>

    fun signOut()
}