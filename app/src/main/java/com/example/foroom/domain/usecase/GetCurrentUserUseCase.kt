package com.example.foroom.domain.usecase

import com.example.foroom.domain.repository.rest.ForoomRestRepository

class GetCurrentUserUseCase(private val repository: ForoomRestRepository) {

    suspend operator fun invoke() = repository.getCurrentUser()

}