package com.example.foroom.domain.usecase

import com.example.foroom.domain.repository.ForoomRepository

class GetEmojisUseCase(private val repository: ForoomRepository) {

    suspend operator fun invoke() = repository.getEmojis()

}