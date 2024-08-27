package com.example.foroom.presentation.ui.screens.home.chats.events

sealed class ForoomHomeChatsEvents {
    data object FilterCreatedChats: ForoomHomeChatsEvents()
    data object FilterFavouriteChats: ForoomHomeChatsEvents()
    data object RefreshChats: ForoomHomeChatsEvents()
}