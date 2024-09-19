package com.example.shared.util.runtime.user_token

class UserTokenRuntimeHolderImpl: UserTokenRuntimeHolder {
    private var userToken: String? = null

    override fun setUserToken(token: String) {
        userToken = token
    }

    override fun getUserToken(): String {
        return BEARER_TOKEN_FORMAT.format(userToken)
    }

    companion object {
        private const val BEARER_TOKEN_FORMAT = "Bearer %s"
    }
}