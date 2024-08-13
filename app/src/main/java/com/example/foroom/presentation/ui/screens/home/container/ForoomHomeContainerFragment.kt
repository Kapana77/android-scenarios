package com.example.foroom.presentation.ui.screens.home.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foroom.R
import com.example.foroom.databinding.FragmentForoomContainerHomeBinding
import com.example.foroom.presentation.ui.screens.home.chats.ForoomHomeChatsFragment
import com.example.foroom.presentation.ui.screens.create_chat.ForoomCreateChatFragment
import com.example.foroom.presentation.ui.screens.home.profile.ForoomProfileFragment
import com.example.navigation.host.openNextPage
import com.example.navigation.util.navigationHost
import com.example.shared.ui.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomHomeContainerFragment :
    BaseFragment<ForoomHomeContainerViewModel, FragmentForoomContainerHomeBinding>() {

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForoomContainerHomeBinding
        get() = FragmentForoomContainerHomeBinding::inflate
    override val viewModel: ForoomHomeContainerViewModel by viewModel()

    private val chatsFragment = ForoomHomeChatsFragment()
    private val profileFragment = ForoomProfileFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigation()
    }

    private fun setUpNavigation() = with(binding.navBar) {
        onNavigationButtonClick = { button ->

            when (button.id) {
                R.id.homeNavigationChats -> {
                    addOrShowPage(chatsFragment)
                }

                R.id.homeNavigationCreateChat -> {
                    navigationHost?.openNextPage(ForoomCreateChatFragment(), animate = false)
                }

                R.id.homeNavigationProfile -> {
                    addOrShowPage(profileFragment)
                }
            }

        }

        selectAt(0)
    }

    private fun addOrShowPage(fragment: BaseFragment<*, *>) {
        val transaction = childFragmentManager.beginTransaction()

        if (fragment.isAdded) {
            childFragmentManager.fragments.forEach(transaction::hide)
            transaction.show(fragment)
        } else {
            transaction.add(
                R.id.homeContainer,
                fragment
            )
        }

        transaction.commit()
    }
}