package com.example.foroom.domain.usecase

import com.example.foroom.domain.repository.rest.ForoomRestRepository

class GetEmojisUseCase(private val repository: ForoomRestRepository) {

    suspend operator fun invoke() = repository.getEmojis()

}