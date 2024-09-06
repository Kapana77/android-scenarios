package com.example.shared.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.shared.ui.viewModel.BaseViewModel
import com.example.shared.util.events.ForoomEventsHubHolder
import com.example.shared.util.language_change.AppLanguageDelegate
import com.example.shared.util.loading.GlobalLoadingDelegate

abstract class BaseFragment<VM : BaseViewModel, T : ViewBinding> : Fragment() {
    abstract val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T
    abstract val viewModel: VM

    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected val globalLoadingDelegate get() = activity as? GlobalLoadingDelegate
    protected val eventsHub get() = (activity as? ForoomEventsHubHolder)?.eventsHub
    protected val appLanguageDelegate get() = activity as? AppLanguageDelegate

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
}