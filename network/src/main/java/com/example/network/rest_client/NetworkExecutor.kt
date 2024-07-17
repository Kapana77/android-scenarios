package com.example.network.rest_client

import androidx.lifecycle.viewModelScope
import com.example.network.Error
import com.example.shared.model.Result
import com.example.shared.ui.viewModel.BaseViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class NetworkExecutor<T> internal constructor(parentJob: Job) {
    private val executorScope = CoroutineScope(Dispatchers.IO + SupervisorJob(parentJob))

    private var onResult: ((Result<T>) -> Unit)? = null
    private var executeBlock: (suspend CoroutineScope.() -> T)? = null
    private var loadingBlock: (() -> Unit)? = null
    private var onSuccessBlock: ((T) -> Unit)? = null
    private var onErrorBlock: ((Exception) -> Unit)? = null
    private var onStartBlock: (() -> Unit)? = null
    private var onFinishBlock: (() -> Unit)? = null

    internal fun makeNetworkCall() = executorScope.launch {
        onStartBlock?.invoke()
        loadingBlock?.invoke()
        onResult?.invoke(Result.Loading)

        try {
            val result = executeBlock?.invoke(executorScope)

            result?.let { r ->
                withContext(Dispatchers.Main) {
                    onSuccessBlock?.invoke(r)
                    onResult?.invoke(Result.Success(r))
                }
            } ?: throw Error.unknownNetworkError
        } catch (e: Exception) {
            if (e is CancellationException) throw e

            onErrorBlock?.invoke(e)
            onResult?.invoke(Result.Error(e))
        }

        onFinishBlock?.invoke()
    }

    fun execute(block: suspend CoroutineScope.() -> T) {
        executeBlock = block
    }

    fun loading(block: () -> Unit) {
        loadingBlock = block
    }

    fun success(block: (T) -> Unit) {
        onSuccessBlock = block
    }

    fun error(block: (Exception) -> Unit) {
        onErrorBlock = block
    }

    fun onStart(block: () -> Unit) {
        onStartBlock = block
    }

    fun onFinish(block: () -> Unit) {
        onFinishBlock = block
    }

    fun onResult(block: (Result<T>) -> Unit) {
        onResult = block
    }
}

fun <T> BaseViewModel.networkExecutor(block: NetworkExecutor<T>.() -> Unit) =
    NetworkExecutor<T>(viewModelScope.coroutineContext.job).apply(block).makeNetworkCall()
