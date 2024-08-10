package com.example.shared.ui.delegate

import kotlinx.coroutines.CoroutineScope

interface ForoomDelegate {
    val parentScope: CoroutineScope

    fun init()

    fun init(scope: CoroutineScope)

    fun <T> init(scope: CoroutineScope, args: T)
}