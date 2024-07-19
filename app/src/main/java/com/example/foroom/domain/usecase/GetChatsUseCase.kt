package com.example.foroom.domain.usecase

import com.example.foroom.domain.model.ChatsResponse
import com.example.foroom.domain.repository.ForoomRepository

class GetChatsUseCase(private val repository: ForoomRepository) {
    suspend operator fun invoke(): ChatsResponse {
        return repository.getChats()
    }
}