package com.example.shared.util.runtime.user_token

interface UserTokenRuntimeHolder {

    fun setUserToken(token: String)

    fun getUserToken(): String
}