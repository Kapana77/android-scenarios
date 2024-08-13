package com.example.shared.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.shared.model.Result

class ResultHandler<T> {
    private var loadingBlock: (() -> Unit)? = null
    private var onSuccessBlock: ((T) -> Unit)? = null
    private var onErrorBlock: ((Exception) -> Unit)? = null
    private var onResultBlock: ((Result<T>)-> Unit)? = null

    fun onSuccess(block: (T) -> Unit) {
        onSuccessBlock = block
    }

    fun onError(block: (Exception) -> Unit) {
        onErrorBlock = block
    }

    fun onLoading(block: () -> Unit) {
        loadingBlock = block
    }

    fun onResult(block: (Result<T>) -> Unit) {
        onResultBlock = block
    }

    fun handle(liveData: LiveData<Result<T>>, lifecycleOwner: LifecycleOwner) {
        liveData.observe(lifecycleOwner) { result ->
            onResultBlock?.invoke(result)

            when (result) {
                is Result.Success -> {
                    onSuccessBlock?.invoke(result.data)
                }

                is Result.Error -> {
                    onErrorBlock?.invoke(result.exception)
                }

                is Result.Loading -> {
                    loadingBlock?.invoke()
                }
            }
        }
    }
}