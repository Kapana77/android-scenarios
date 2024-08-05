package com.example.foroom.domain.usecase

import com.example.foroom.domain.model.response.ChatsResponse
import com.example.foroom.domain.repository.rest.ForoomRestRepository

class GetChatsUseCase(private val repository: ForoomRestRepository) {
    suspend operator fun invoke(
        page: Int,
        limit: Int = DEFAULT_LIMIT,
        name: String? = null,
        popular: Boolean = false,
        created: Boolean = false,
        favorite: Boolean = false
    ): ChatsResponse {
        return repository.getChats(page, limit, name, popular, created, favorite)
    }

    companion object {
        private const val DEFAULT_LIMIT = 20
    }
}