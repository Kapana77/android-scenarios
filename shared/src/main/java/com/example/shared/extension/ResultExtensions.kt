package com.example.shared.extension

import com.example.shared.model.Result

val Result<*>.isSuccess get() = this is Result.Success<*>

val Result<*>.isError get() = this is Result.Error

val Result<*>.isLoading get() = this is Result.Loading

val <T> Result<T>.successValueOrNull get() = (this as? Result.Success<T>)?.data
