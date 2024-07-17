package com.example.network

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException

inline fun <reified ERROR> Exception.ifHttpError(block: (Int, ERROR) -> Unit): Boolean {
    if (this is HttpException) {
        val errorObject = try {
            Gson().fromJson(response()?.errorBody()?.string(), ERROR::class.java)
        } catch (_: JsonSyntaxException) {
            return false
        }

        block(code(), errorObject)
    }

    return false
}
