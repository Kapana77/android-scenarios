package com.example.shared.extension

import com.example.shared.model.Result

val Result<*>.isSuccess get() = this is Result.Success<*>

val Result<*>.isLoading get() = this is Result.Loading
