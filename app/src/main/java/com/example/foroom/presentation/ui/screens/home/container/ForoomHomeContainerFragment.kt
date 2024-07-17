package com.example.foroom.presentation.ui.screens.home.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.foroom.R
import com.example.foroom.databinding.FragmentForoomContainerHomeBinding
import com.example.foroom.presentation.ui.screens.home.chats.ForoomChatsFragment
import com.example.foroom.presentation.ui.screens.home.create_chat.ForoomCreateChatFragment
import com.example.foroom.presentation.ui.screens.home.profile.ForoomProfileFragment
import com.example.navigation.host.ForoomNavigationHost
import com.example.navigation.host.addOrShowPage
import com.example.shared.ui.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomHomeContainerFragment :
    BaseFragment<ForoomHomeContainerViewModel, FragmentForoomContainerHomeBinding>(),
    ForoomNavigationHost {

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForoomContainerHomeBinding
        get() = FragmentForoomContainerHomeBinding::inflate
    override val viewModel: ForoomHomeContainerViewModel by viewModel()

    private val chatsFragment = ForoomChatsFragment()
    private val createChatFragment = ForoomCreateChatFragment()
    private val profileFragment = ForoomProfileFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigation()
    }

    private fun setUpNavigation() = with(binding.navBar){
        onNavigationButtonClick = { button ->

            when(button.id) {
                R.id.homeNavigationChats -> {
                    addOrShowPage(chatsFragment)
                }

                R.id.homeNavigationCreateChat -> {
                    addOrShowPage(createChatFragment)
                }

                R.id.homeNavigationProfile -> {
                    addOrShowPage(profileFragment)
                }
            }

        }

        selectAt(0)
    }

    override fun getHostFragmentManager(): FragmentManager {
        return childFragmentManager
    }

    override val fragmentContainerId: Int = R.id.homeContainer

    override fun goBack() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}