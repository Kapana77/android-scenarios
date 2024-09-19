package com.example.shared.di

import com.example.shared.util.events.ForoomEventsHub
import com.example.shared.util.events.ForoomEventsHubImpl
import com.example.shared.util.lock.SafeInteractionLock
import com.example.shared.util.lock.SafeInteractionLockImpl
import com.example.shared.util.runtime.user_language.UserLanguageRuntimeHolder
import com.example.shared.util.runtime.user_language.UserLanguageRuntimeHolderImpl
import com.example.shared.util.runtime.user_token.UserTokenRuntimeHolder
import com.example.shared.util.runtime.user_token.UserTokenRuntimeHolderImpl
import org.koin.dsl.module

val sharedModule = module {
    factory<SafeInteractionLock> { params ->
        SafeInteractionLockImpl(params.get())
    }

    single<ForoomEventsHub> {
        ForoomEventsHubImpl()
    }

    single<UserTokenRuntimeHolder> {
        UserTokenRuntimeHolderImpl()
    }

    single<UserLanguageRuntimeHolder> {
        UserLanguageRuntimeHolderImpl()
    }
}