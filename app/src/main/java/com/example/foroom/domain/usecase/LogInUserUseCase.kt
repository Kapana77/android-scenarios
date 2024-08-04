package com.example.foroom.domain.usecase

import com.example.foroom.domain.model.request.LogInRequest
import com.example.foroom.domain.repository.rest.ForoomRestRepository

class LogInUserUseCase(private val repository: ForoomRestRepository) {

    suspend operator fun invoke(request: LogInRequest) = repository.logInUser(request)
}