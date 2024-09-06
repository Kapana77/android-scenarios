package com.example.foroom.presentation.ui.screens.home.profile.bottom_sheets.change_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.design_system.components.bottom_sheet.ForoomActionBottomSheetFragment
import com.example.design_system.components.input.Input
import com.example.foroom.R
import com.example.foroom.databinding.LayoutChangePasswordBottomSheetBinding
import com.example.foroom.presentation.ui.screens.home.profile.bottom_sheets.change_language.ForoomChangeLanguageViewModel
import com.example.foroom.presentation.ui.screens.home.profile.events.ProfileScreenEvents
import com.example.foroom.presentation.ui.util.validator.BlankInputValidation
import com.example.network.ifHttpError
import com.example.network.model.response.AuthenticationError
import com.example.shared.extension.handleResult
import com.example.shared.extension.ifNot
import com.example.shared.extension.orEmpty
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomChangePasswordBottomSheet :
    ForoomActionBottomSheetFragment<ForoomChangeLanguageViewModel, LayoutChangePasswordBottomSheetBinding>() {

    override val inflateContent: (LayoutInflater, ViewGroup?, Boolean) -> LayoutChangePasswordBottomSheetBinding =
        LayoutChangePasswordBottomSheetBinding::inflate
    override val viewModel: ForoomChangeLanguageViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setObservers()
    }

    override fun onActionButtonClick() {
        if (!binding.passwordInput.validate() || !binding.repeatPasswordInput.validate()) return

        val password = binding.passwordInput.text
        val repeatPassword = binding.repeatPasswordInput.text

        if (password != repeatPassword) {
            binding.repeatPasswordInput.setInputDescription(
                R.string.password_does_not_match_error,
                Input.DescriptionType.ERROR,
                true
            )

            return
        }

        viewModel.changePassword(password)
    }

    private fun initViews() {
        buttonText = getString(R.string.confirm)

        binding.passwordInput.addValidation(BlankInputValidation)
        binding.repeatPasswordInput.addValidation(BlankInputValidation)
    }

    private fun setObservers() {
        viewModel.changePasswordLiveData.handleResult(viewLifecycleOwner) {
            onSuccess {
                dismiss()
                eventsHub?.sendEvent(ProfileScreenEvents.SignOut)
            }

            onError { error ->
                error.ifHttpError<AuthenticationError> { _, e ->
                    binding.passwordInput.setInputDescription(
                        e.passwordError.orEmpty(),
                        Input.DescriptionType.ERROR,
                        true
                    )
                }.ifNot {
                    // todo handle error
                }

                isCancelable = true
            }

            onLoading {
                // todo handle loading
                isCancelable = false
            }
        }
    }
}