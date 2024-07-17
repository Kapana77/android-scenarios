package com.example.foroom.domain.usecase

import com.example.foroom.domain.repository.ForoomRepository

class GetAvatarsUseCase(private val repository: ForoomRepository) {

    suspend operator fun invoke() = repository.getAvatars()

}