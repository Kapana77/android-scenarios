package com.example.shared.util.events

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import kotlin.reflect.KClass

internal class ForoomEventsHubImpl : ForoomEventsHub {
    private val eventsLiveData = MutableLiveData<Any?>()

    @Suppress("UNCHECKED_CAST")
    override fun <T: Any> observeEvent(
        lifecycleOwner: LifecycleOwner,
        eventClass: KClass<T>,
        onEvent: (T) -> Unit
    ) {
        eventsLiveData.observe(lifecycleOwner) { event ->
            if (event != null && event::class == eventClass) {
                onEvent(event as T)
                eventsLiveData.value = null
            }
        }
    }

    override fun sendEvent(event: Any) {
        eventsLiveData.value = event
    }

    override fun postEvent(event: Any) {
        eventsLiveData.postValue(event)
    }
}