package com.example.shared.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shared.ui.delegate.BaseForoomDelegate
import com.example.shared.ui.delegate.ForoomDelegate
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent

abstract class BaseViewModel: ViewModel(), KoinComponent {
    private val _errorMessageLiveData = MutableLiveData<Int>()
    val errorMessageLiveData: LiveData<Int> get() = _errorMessageLiveData

    var requestCode = RequestCode.RC_INIT
        protected set

    protected fun sendErrorMessage(messageRes: Int) {
        _errorMessageLiveData.value = messageRes
    }

    protected fun ForoomDelegate.init() {
        (this as BaseForoomDelegate).init()
    }

    protected fun ForoomDelegate.init(scope: CoroutineScope) {
        (this as BaseForoomDelegate).init(scope)
    }

    protected fun <T> ForoomDelegate.init(scope: CoroutineScope, args: T) {
        (this as BaseForoomDelegate).init(scope, args)
    }

    enum class RequestCode {
        RC_INIT,
        RC_LOAD_MORE;

        fun isInit() = this == RC_INIT
        fun isLoadMore() = this == RC_LOAD_MORE
    }
}