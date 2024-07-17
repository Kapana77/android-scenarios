package com.example.foroom.domain.usecase

import com.example.foroom.domain.model.Chat
import com.example.foroom.domain.repository.ForoomRepository
import kotlinx.coroutines.delay

class GetChatsUseCase(private val repository: ForoomRepository) {

    suspend operator fun invoke(): List<Chat> {
        return repository.getChats()
    }


}