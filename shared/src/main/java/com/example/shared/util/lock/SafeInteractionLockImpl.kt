package com.example.shared.util.lock

class SafeInteractionLockImpl(private var safetyInterval: Long) : SafeInteractionLock {
    private var lastInteractionTime: Long = 0;

    override var interactionCallBack: (() -> Unit)? = null

    override val isSafeToInteract: Boolean get() =
        System.currentTimeMillis() - lastInteractionTime > safetyInterval

    override fun setSafetyInterval(interval: Long) {
        safetyInterval = interval
    }

    override fun interact() {
        if (isSafeToInteract) {
            lastInteractionTime = System.currentTimeMillis()
            interactionCallBack?.invoke()
        }
    }
}