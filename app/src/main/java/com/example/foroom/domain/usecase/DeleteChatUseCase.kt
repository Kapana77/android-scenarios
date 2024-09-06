package com.example.foroom.domain.usecase

import com.example.foroom.domain.repository.rest.ForoomRestRepository

class DeleteChatUseCase(private val repository: ForoomRestRepository) {

    suspend operator fun invoke(id: Int) {
        repository.deleteChat(id)
    }
}