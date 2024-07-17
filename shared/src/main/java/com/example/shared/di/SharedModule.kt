package com.example.shared.di

import com.example.shared.util.lock.SafeInteractionLock
import com.example.shared.util.lock.SafeInteractionLockImpl
import org.koin.dsl.module

val sharedModule = module {
    factory<SafeInteractionLock> { params ->
        SafeInteractionLockImpl(params.get())
    }
}