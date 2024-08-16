package com.example.shared.util.events

import androidx.lifecycle.LifecycleOwner
import kotlin.reflect.KClass

interface ForoomEventsHub {

    fun <T: Any> observeEvent(
        lifecycleOwner: LifecycleOwner,
        eventClass: KClass<T>,
        onEvent: (T) -> Unit
    )

    fun sendEvent(event: Any)

    fun postEvent(event: Any)
}

inline fun <reified T: Any> ForoomEventsHub.observeEvent(
    lifecycleOwner: LifecycleOwner,
    noinline onEvent: (T) -> Unit
) {
    observeEvent(lifecycleOwner, T::class, onEvent)
}

