package com.example.foroom.presentation.ui.di

import com.example.foroom.domain.model.Chat
import com.example.foroom.presentation.ui.screens.home.chats.ForoomChatsViewModel
import com.example.foroom.presentation.ui.screens.home.container.ForoomHomeContainerViewModel
import com.example.foroom.presentation.ui.screens.home.create_chat.ForoomCreateChatViewModel
import com.example.foroom.presentation.ui.screens.home.profile.ForoomProfileViewModel
import com.example.foroom.presentation.ui.screens.log_in.ForoomLoginViewModel
import com.example.foroom.presentation.ui.screens.registration.ForoomRegistrationViewModel
import com.example.shared.util.pagination.PaginationHelper
import com.example.shared.util.pagination.PaginationHelperImpl
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule get() = module {
    includes(registrationModule, logInModule, homeModule)

    factory<PaginationHelper<Chat>> { PaginationHelperImpl() }
}

val registrationModule = module {
    viewModelOf(::ForoomRegistrationViewModel)
}

val logInModule = module {
    viewModelOf(::ForoomLoginViewModel)
}

val homeModule = module {
    viewModelOf(::ForoomHomeContainerViewModel)

    // chats
    viewModelOf(::ForoomChatsViewModel)

    // profile
    viewModelOf(::ForoomProfileViewModel)

    // create chat
    viewModelOf(::ForoomCreateChatViewModel)
}
