package com.example.foroom.di

import com.example.foroom.domain.usecase.ChangePasswordUseCase
import com.example.foroom.domain.usecase.ChangeUserAvatarUseCase
import com.example.foroom.domain.usecase.ChangeUsernameUseCase
import com.example.foroom.domain.usecase.CreateChatUseCase
import com.example.foroom.domain.usecase.ChangeChatIsFavoriteUseCase
import com.example.foroom.domain.usecase.GetAvatarsUseCase
import com.example.foroom.domain.usecase.GetChatsUseCase
import com.example.foroom.domain.usecase.GetCurrentUserUseCase
import com.example.foroom.domain.usecase.GetEmojisUseCase
import com.example.foroom.domain.usecase.GetMessageHistoryUseCase
import com.example.foroom.domain.usecase.LogInUserUseCase
import com.example.foroom.domain.usecase.MessageWebSocketUseCase
import com.example.foroom.domain.usecase.RegisterUserUseCase
import com.example.foroom.domain.usecase.RemoteSignOutUseCase
import org.koin.dsl.module

val domainModule = module {
    single {
        GetAvatarsUseCase(get())
    }

    single {
        GetEmojisUseCase(get())
    }

    single {
        RegisterUserUseCase(get())
    }

    single {
        LogInUserUseCase(get())
    }

    single {
        GetChatsUseCase(get())
    }

    single {
        MessageWebSocketUseCase(get())
    }

    single {
        GetCurrentUserUseCase(get())
    }

    single {
        GetMessageHistoryUseCase(get())
    }

    single {
        CreateChatUseCase(get())
    }

    single {
        ChangeUserAvatarUseCase(get())
    }

    single {
        ChangeUsernameUseCase(get())
    }

    single {
        ChangePasswordUseCase(get())
    }

    single {
        RemoteSignOutUseCase(get())
    }

    single {
        ChangeChatIsFavoriteUseCase(get())
    }
}
