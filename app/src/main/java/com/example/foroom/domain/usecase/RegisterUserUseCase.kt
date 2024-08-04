package com.example.foroom.domain.usecase

import com.example.foroom.domain.model.request.RegistrationRequest
import com.example.foroom.domain.repository.rest.ForoomRestRepository

class RegisterUserUseCase(private val repository: ForoomRestRepository) {

    suspend operator fun invoke(request: RegistrationRequest) = repository.registerUser(request)

}