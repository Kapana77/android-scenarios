package com.example.foroom.domain.usecase

import com.example.foroom.domain.repository.rest.ForoomRestRepository

class ChangeUserAvatarUseCase(
    private val repository: ForoomRestRepository
) {
    suspend operator fun invoke(avatarId: Int) {
        repository.changeAvatar(avatarId)
    }
}