package com.example.foroom.presentation.ui.screens.log_in

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.design_system.components.input.Input
import com.example.foroom.databinding.FragmentForoomLogInBinding
import com.example.foroom.presentation.ui.screens.home.container.ForoomHomeContainerFragment
import com.example.foroom.presentation.ui.screens.registration.ForoomRegistrationFragment
import com.example.foroom.presentation.ui.util.validator.BlankInputValidation
import com.example.navigation.host.openNextPage
import com.example.navigation.util.navigationHost
import com.example.network.ifHttpError
import com.example.network.model.response.AuthenticationError
import com.example.shared.extension.handleResult
import com.example.shared.extension.ifNot
import com.example.shared.extension.isLoading
import com.example.shared.extension.onClick
import com.example.shared.extension.orEmpty
import com.example.shared.extension.toast
import com.example.shared.ui.fragment.BaseFragment
import com.example.shared.util.loading.isLoading
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class ForoomLoginFragment: BaseFragment<ForoomLoginViewModel, FragmentForoomLogInBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForoomLogInBinding
        get() = FragmentForoomLogInBinding::inflate
    override val viewModel: ForoomLoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setListeners()
        setObservers()
    }

    private fun initViews() {
        initInputs()

        lifecycleScope.launch {
            viewModel.getUserLanguage()?.let { language ->
                binding.languageSelector.selectLanguage(language)
            }
        }
    }

    private fun setListeners() {
        binding.signUpButton.onClick {
            navigationHost?.openNextPage(ForoomRegistrationFragment())
        }

        binding.logInButton.onClick {
            val userNameValid = binding.userNameInput.validate()
            val passwordValid = binding.passwordInput.validate()

            if (userNameValid && passwordValid) viewModel.logIn()
        }

        binding.languageSelector.onLanguageSelected = { language ->
            lifecycleScope.launch {
                viewModel.updateUserLanguage(language)
                activity?.recreate()
            }
        }
    }

    private fun setObservers() {
        viewModel.logInLiveData.handleResult(viewLifecycleOwner) {
            onError {  exception ->
                handleLoginError(exception)
            }

            onResult { result ->
                globalLoadingDelegate?.isLoading(result.isLoading)
            }
        }

        viewModel.getAndSaveUserResultLiveData.handleResult(viewLifecycleOwner) {
            onSuccess {
                navigationHost?.openNextPage(ForoomHomeContainerFragment(), popBackStack = true)
            }

            onError {
                Log.d("logkata", "aeeee" + it.message.toString())
            }
        }
    }

    private fun handleLoginError(exception: Exception) {
        exception.ifHttpError<AuthenticationError> { _, error ->
            error.usernameError?.let { message ->
                binding.userNameInput.setInputDescription(
                    message,
                    Input.DescriptionType.ERROR,
                    true
                )
            }

            error.passwordError?.let { message ->
                binding.passwordInput.setInputDescription(
                    message,
                    Input.DescriptionType.ERROR,
                    true
                )
            }
        }.ifNot {
            context?.toast(exception.message)
        }
    }

    private fun initInputs() {
        with(binding.userNameInput) {
            addValidation(BlankInputValidation)
            editText.addTextChangedListener { text ->
                viewModel.userName = text?.toString().orEmpty()
            }
        }

        with(binding.passwordInput) {
            addValidation(BlankInputValidation)
            editText.addTextChangedListener { text ->
                viewModel.password = text?.toString().orEmpty()
            }
        }
    }
}