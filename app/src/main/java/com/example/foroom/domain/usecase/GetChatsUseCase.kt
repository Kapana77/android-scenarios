package com.example.foroom.domain.usecase

import com.example.foroom.domain.model.response.ChatsResponse
import com.example.foroom.domain.repository.rest.ForoomRestRepository

class GetChatsUseCase(private val repository: ForoomRestRepository) {
    suspend operator fun invoke(page: Int, limit: Int = DEFAULT_LIMIT): ChatsResponse {
        return repository.getChats(page, limit)
    }

    companion object {
        private const val DEFAULT_LIMIT = 20
    }
}