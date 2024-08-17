package com.example.foroom.presentation.ui.screens.home.profile.events

sealed class ProfileScreenEvents {
    data class LocalProfileImageChange(val newImageUrl: String): ProfileScreenEvents()
    data object ReloadProfile: ProfileScreenEvents()
}