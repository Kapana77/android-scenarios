package com.example.foroom.presentation.ui.screens.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.design_system.components.shimmer.ForoomShimmerDrawableBuilder
import com.example.foroom.databinding.FragmentForoomProfileBinding
import com.example.foroom.presentation.ui.screens.home.chats.events.ForoomHomeChatsEvents
import com.example.foroom.presentation.ui.screens.home.container.events.ForoomHomeEvents
import com.example.foroom.presentation.ui.screens.home.container.events.HomeNavigationType
import com.example.shared.extension.loadImageUrl
import com.example.shared.extension.onClick
import com.example.shared.ui.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomProfileFragment : BaseFragment<ForoomProfileViewModel, FragmentForoomProfileBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForoomProfileBinding
        get() = FragmentForoomProfileBinding::inflate
    override val viewModel: ForoomProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setListeners()
        setObservers()
    }

    private fun initViews() {
        binding.userImageView.image.setImageDrawable(
            ForoomShimmerDrawableBuilder.getDefaultDrawable(requireContext())
        )
    }

    private fun setListeners() {
        binding.createdChatsItem.onClick {
            eventsHub?.sendEvent(ForoomHomeEvents.HomeNavigationEvents(HomeNavigationType.CHATS))
            eventsHub?.sendEvent(ForoomHomeChatsEvents.FilterCreatedChats)
        }

        binding.favoriteChatsItem.onClick {
            eventsHub?.sendEvent(ForoomHomeEvents.HomeNavigationEvents(HomeNavigationType.CHATS))
            eventsHub?.sendEvent(ForoomHomeChatsEvents.FilterFavouriteChats)
        }
    }

    private fun setObservers() {
        viewModel.currentUserLiveData.observe(viewLifecycleOwner) { user ->
            binding.userImageView.image.loadImageUrl(
                user.avatarUrl,
                ForoomShimmerDrawableBuilder.getDefaultDrawable(requireContext())
            )

            binding.userNameTextView.text = user.userName
        }
    }
}