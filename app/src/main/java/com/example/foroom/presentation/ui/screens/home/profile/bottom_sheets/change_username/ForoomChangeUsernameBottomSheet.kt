package com.example.foroom.presentation.ui.screens.home.profile.bottom_sheets.change_username

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.design_system.components.bottom_sheet.ForoomActionBottomSheetFragment
import com.example.design_system.components.input.Input
import com.example.design_system.components.input.validator.InputValidation
import com.example.foroom.R
import com.example.foroom.databinding.LayoutChangeUsernameBottomSheetBinding
import com.example.foroom.presentation.ui.screens.home.profile.events.ProfileScreenEvents
import com.example.foroom.presentation.ui.util.validator.BlankInputValidation
import com.example.foroom.presentation.ui.util.validator.SizeInputValidation
import com.example.network.ifHttpError
import com.example.shared.extension.handleResult
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
                // todo handle error
                error.localizedMessage?.let { message ->
                    binding.root.setInputDescription(message, Input.DescriptionType.ERROR, true)
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