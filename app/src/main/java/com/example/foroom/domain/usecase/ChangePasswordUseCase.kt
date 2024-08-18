package com.example.foroom.domain.usecase

import com.example.foroom.domain.repository.rest.ForoomRestRepository

class ChangePasswordUseCase(private val repository: ForoomRestRepository) {

    suspend operator fun invoke(newPassword: String) {
        repository.changePassword(newPassword)
    }
}