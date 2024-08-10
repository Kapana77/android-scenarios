package com.example.network.auth

interface UserTokenRuntimeHolder {

    fun setUserToken(token: String)

    fun getUserToken(): String

    companion object {
        const val BEARER_TOKEN_KEY = "Authorization"
    }
}