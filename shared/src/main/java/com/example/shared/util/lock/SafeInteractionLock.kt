package com.example.shared.util.lock

interface SafeInteractionLock {
    val isSafeToInteract: Boolean
    var interactionCallBack: (() -> Unit)?

    fun setSafetyInterval(interval: Long)
    fun interact()
}