package com.example.foroom.presentation.ui.screens.home.profile.bottom_sheets.change_profile_picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.design_system.components.bottom_sheet.ForoomActionBottomSheetFragment
import com.example.foroom.R
import com.example.foroom.databinding.LayoutChangeProfilePictureBottomSheetBinding
import com.example.foroom.presentation.ui.screens.home.profile.events.ProfileScreenEvents
import com.example.shared.extension.handleResult
import com.example.shared.model.Image
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomChangeProfilePictureBottomSheet :
    ForoomActionBottomSheetFragment<ForoomChangeProfilePictureViewModel, LayoutChangeProfilePictureBottomSheetBinding>() {
    override val inflateContent: (LayoutInflater, ViewGroup?, Boolean) -> LayoutChangeProfilePictureBottomSheetBinding
        get() = LayoutChangeProfilePictureBottomSheetBinding::inflate
    override val viewModel: ForoomChangeProfilePictureViewModel by viewModel()

    private val originalImageUrl get() = arguments?.getString(ORIGINAL_IMAGE_URL_KEY)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setListeners()
        setObservers()
    }

    override fun onActionButtonClick() {
        viewModel.changeUserAvatar()
    }

    private fun initViews() {
        buttonText = getString(R.string.confirm)
    }

    private fun setListeners() {
        binding.root.onImageChooseCallback = { image ->
            if (binding.root.isChoosingEnabled)
                eventsHub?.sendEvent(ProfileScreenEvents.LocalProfileImageChange(image.url))
            viewModel.selectedAvatarId = image.id
        }
    }

    private fun setObservers() {
        viewModel.avatarsLiveData.handleResult(viewLifecycleOwner) {
            onLoading {
                binding.root.isChoosingEnabled = false
                binding.root.images = Image.getBlankImages(BLANK_IMAGES_SIZE)
            }

            onSuccess { images ->
                val index = images.indexOfFirst { image ->
                    image.url == originalImageUrl
                }

                binding.root.selectImageAt(index, true)
                binding.root.images = images
                binding.root.isChoosingEnabled = true

                viewModel.selectedAvatarId = images[index].id
            }

            onError {
                dismiss()
            }
        }

        viewModel.changeUserAvatarLiveData.handleResult(viewLifecycleOwner) {
            onSuccess {
                eventsHub?.sendEvent(ProfileScreenEvents.ReloadProfile)
                dismiss()
            }

            onError {
                isCancelable = true
            }

            onLoading {
                isCancelable = false
            }
        }
    }

    companion object {
        private const val BLANK_IMAGES_SIZE = 6

        private const val ORIGINAL_IMAGE_URL_KEY = "originalImageUrl"
        fun newInstance(originalImageUrl: String) = ForoomChangeProfilePictureBottomSheet().apply {
            arguments = bundleOf(ORIGINAL_IMAGE_URL_KEY to originalImageUrl)
        }
    }
}