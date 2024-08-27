package com.example.foroom.domain.usecase

import com.example.foroom.domain.repository.rest.ForoomRestRepository

class CreateChatUseCase(private val repository: ForoomRestRepository) {
    suspend operator fun invoke(name: String, emojiId: Int) = repository.createChat(name, emojiId)
}