package com.example.foroom.presentation.ui.delegate.sign_out

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foroom.domain.usecase.RemoteSignOutUseCase
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.network.rest_client.parentNetworkExecutor
import com.example.shared.ui.delegate.BaseForoomDelegate
import com.example.shared.model.Result

class SignOutDelegateImpl(
    private val remoteSignOutUseCase: RemoteSignOutUseCase,
    private val userDataStore: ForoomUserDataStore
): SignOutDelegate, BaseForoomDelegate() {
    private val _signOutLiveData = MutableLiveData<Result<Unit>>()
    override val signOutLiveData: LiveData<Result<Unit>> get() = _signOutLiveData

    override fun signOut() {
        parentNetworkExecutor {
            execute {
                userDataStore.clearUserData()
                remoteSignOutUseCase()
            }

            onResult { result ->
                _signOutLiveData.postValue(result)
            }
        }
    }
}