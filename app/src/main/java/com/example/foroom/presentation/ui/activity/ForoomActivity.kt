package com.example.foroom.presentation.ui.activity

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.example.foroom.R
import com.example.foroom.databinding.ActivityForoomBinding
import com.example.foroom.presentation.ui.screens.home.container.ForoomHomeContainerFragment
import com.example.navigation.host.ForoomNavigationHost
import com.example.navigation.host.openNextPage
import com.example.shared.ui.activity.BaseActivity
import com.example.shared.util.loading.GlobalLoadingDelegate

class ForoomActivity : ForoomNavigationHost, GlobalLoadingDelegate,
    BaseActivity<ActivityForoomBinding>(ActivityForoomBinding::inflate) {
    override val fragmentContainerId: Int = R.id.fragmentContainerView
    override fun getHostFragmentManager(): FragmentManager {
        return supportFragmentManager
    }

    override fun goBack() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openNextPage(ForoomHomeContainerFragment(), false)

        // prevent background clicks
        binding.loadingViewBackground.setOnClickListener {}
    }

    override fun showGlobalLoading() {
        globalLoadingVisible(true)
    }

    override fun hideGlobalLoading() {
        globalLoadingVisible(false)
    }

    private fun globalLoadingVisible(visible: Boolean) {
        binding.loadingViewBackground.isVisible = visible

        with(binding.loadingLottie) {
            isVisible = visible
            if (visible) playAnimation()
            else cancelAnimation()
        }
    }
}
