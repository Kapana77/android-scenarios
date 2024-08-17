package com.example.shared.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.shared.model.Result
import com.example.shared.ui.ResultHandler

fun <T> LiveData<Result<T>>.handleResult(
    lifecycleOwner: LifecycleOwner,
    block: ResultHandler<T>.() -> Unit
) = ResultHandler<T>().apply(block).handle(this, lifecycleOwner)
