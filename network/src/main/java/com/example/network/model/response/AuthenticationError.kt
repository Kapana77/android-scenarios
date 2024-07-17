package com.example.network.model.response

data class AuthenticationError(
    val usernameError: String?,
    val passwordError: String?
)