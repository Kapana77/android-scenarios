package com.example.foroom.domain.usecase

import com.example.foroom.domain.repository.rest.ForoomRestRepository

class ChangeUsernameUseCase(private val repository: ForoomRestRepository) {

    suspend operator fun invoke(newUsername: String) {
        repository.changeUsername(newUsername)
    }
}