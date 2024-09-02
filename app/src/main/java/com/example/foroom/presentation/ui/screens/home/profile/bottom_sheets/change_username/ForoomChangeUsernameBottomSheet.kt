package com.example.foroom.presentation.ui.screens.home.profile.bottom_sheets.change_username

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.design_system.components.bottom_sheet.ForoomActionBottomSheetFragment
import com.example.design_system.components.input.Input
import com.example.foroom.R
import com.example.foroom.databinding.LayoutChangeUsernameBottomSheetBinding
import com.example.foroom.presentation.ui.screens.home.profile.events.ProfileScreenEvents
import com.example.foroom.presentation.ui.util.validator.BlankInputValidation
import com.example.foroom.presentation.ui.util.validator.SizeInputValidation
import com.example.network.ifHttpError
import com.example.network.model.response.AuthenticationError
import com.example.shared.extension.handleResult
import com.example.shared.extension.ifNot
import com.example.shared.extension.orEmpty
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomChangeUsernameBottomSheet :
    ForoomActionBottomSheetFragment<ForoomChangeUsernameViewModel, LayoutChangeUsernameBottomSheetBinding>() {

    override val inflateContent: (LayoutInflater, ViewGroup?, Boolean) -> LayoutChangeUsernameBottomSheetBinding
        get() = LayoutChangeUsernameBottomSheetBinding::inflate
    override val viewModel: ForoomChangeUsernameViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setObservers()
    }

    override fun onActionButtonClick() {
        if (binding.root.validate()) viewModel.changeUsername(binding.root.text)
    }

    private fun initViews() {
        buttonText = getString(R.string.confirm)

        binding.root.addValidations(
            BlankInputValidation,
            SizeInputValidation(USERNAME_MIN_SIZE, getString(R.string.username_size_error))
        )
    }

    private fun setObservers() {
        viewModel.changeUsernameLiveData.handleResult(viewLifecycleOwner) {
            onSuccess {
                eventsHub?.sendEvent(ProfileScreenEvents.ReloadProfile)
                dismiss()
            }

            onError { error ->
                error.ifHttpError<AuthenticationError> { _, e ->
                    binding.root.setInputDescription(
                        e.usernameError.orEmpty(),
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

    companion object {
        private const val USERNAME_MIN_SIZE = 3
    }
}