package com.example.foroom.presentation.ui.screens.home.container.events

enum class HomeNavigationType {
    CHATS,
    PROFILE
}

sealed class ForoomHomeEvents {
    data class HomeNavigationEvents(val type: HomeNavigationType): ForoomHomeEvents()
}
