package com.example.shared.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent

abstract class BaseViewModel: ViewModel(), KoinComponent {
    private val _errorMessageLiveData = MutableLiveData<Int>()
    val errorMessageLiveData: LiveData<Int> get() = _errorMessageLiveData

    var requestCode = RequestCode.RC_INIT
        protected set

    protected fun sendErrorMessage(messageRes: Int) {
        _errorMessageLiveData.value = messageRes
    }

    enum class RequestCode {
        RC_INIT,
        RC_LOAD_MORE;

        fun isInit() = this == RC_INIT
        fun isLoadMore() = this == RC_LOAD_MORE
    }
}