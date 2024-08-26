package com.example.shared.ui.delegate

import kotlinx.coroutines.CoroutineScope

abstract class BaseForoomDelegate: ForoomDelegate {
    private var _parentScope: CoroutineScope? = null
    val parentScope: CoroutineScope
        get() = requireNotNull(_parentScope) {
        "You should call init(viewModelScope) in your viewModel"
    }

    open fun init() {}

    open fun init(scope: CoroutineScope) {
        init()
        _parentScope = scope
    }

    open fun <T> init(scope: CoroutineScope, args: T) {
        init(scope)
    }
}