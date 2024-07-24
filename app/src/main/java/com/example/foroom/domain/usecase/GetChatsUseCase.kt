package com.example.foroom.domain.usecase

import com.example.foroom.domain.model.ChatsResponse
import com.example.foroom.domain.repository.ForoomRepository

class GetChatsUseCase(private val repository: ForoomRepository) {
    suspend operator fun invoke(page: Int, limit: Int = DEFAULT_LIMIT): ChatsResponse {
        return repository.getChats(page, limit)
    }

    companion object {
        private const val DEFAULT_LIMIT = 20
    }
}