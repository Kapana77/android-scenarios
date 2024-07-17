package com.example.foroom.domain.usecase

import com.example.foroom.domain.model.LogInRequest
import com.example.foroom.domain.repository.ForoomRepository

class LogInUserUseCase(private val repository: ForoomRepository) {

    suspend operator fun invoke(request: LogInRequest) = repository.logInUser(request)
}