package com.example.shared.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.example.shared.model.Result
import com.example.shared.ui.viewModel.BaseViewModel
import com.example.shared.util.loading.GlobalLoadingDelegate

abstract class BaseFragment<VM : BaseViewModel, T : ViewBinding> : Fragment() {
    abstract val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T
    abstract val viewModel: VM

    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected val globalLoadingDelegate get() = activity as? GlobalLoadingDelegate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return _binding?.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Prevent clicks on previous fragments when using add transaction
        view.setOnTouchListener { _, _ -> true }
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { messageRes ->
            Toast.makeText(
                requireContext(),
                requireContext().getString(messageRes),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    protected fun <T> LiveData<Result<T>>.handleResult(block: ResultHandler<T>.() -> Unit) =
        ResultHandler<T>().apply(block).handle(this, viewLifecycleOwner)

    protected class ResultHandler<T> {
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
}