package com.example.shared.ui.delegate

import kotlinx.coroutines.CoroutineScope

abstract class BaseForoomDelegate: ForoomDelegate {
    private var _parentScope: CoroutineScope? = null
    override val parentScope: CoroutineScope
        get() = requireNotNull(_parentScope) {
        "You should call init(viewModelScope) in your viewModel"
    }

    override fun init() {}

    override fun init(scope: CoroutineScope) {
        init()
        _parentScope = scope
    }

    override fun <T> init(scope: CoroutineScope, args: T) {
        init(scope)
    }
}