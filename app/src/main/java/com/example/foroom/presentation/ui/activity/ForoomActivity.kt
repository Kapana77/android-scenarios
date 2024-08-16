package com.example.foroom.presentation.ui.activity

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.example.foroom.R
import com.example.foroom.databinding.ActivityForoomBinding
import com.example.foroom.presentation.ui.screens.home.container.ForoomHomeContainerFragment
import com.example.foroom.presentation.ui.screens.log_in.ForoomLoginFragment
import com.example.navigation.host.ForoomNavigationHost
import com.example.navigation.host.openNextPage
import com.example.shared.ui.activity.BaseActivity
import com.example.shared.util.events.ForoomEventsHub
import com.example.shared.util.events.ForoomEventsHubHolder
import com.example.shared.util.loading.GlobalLoadingDelegate
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomActivity : ForoomNavigationHost, GlobalLoadingDelegate, ForoomEventsHubHolder,
    BaseActivity<ActivityForoomBinding>(ActivityForoomBinding::inflate) {
    override val fragmentContainerId: Int = R.id.fragmentContainerView
    private val viewModel by viewModel<ForoomActivityViewModel>()
    override val eventsHub: ForoomEventsHub = get()

    override fun getHostFragmentManager(): FragmentManager {
        return supportFragmentManager
    }

    override fun goBack() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // prevent background clicks
        binding.loadingViewBackground.setOnClickListener {}
        setObservers()
    }

    override fun showGlobalLoading() {
        globalLoadingVisible(true)
    }

    override fun hideGlobalLoading() {
        globalLoadingVisible(false)
    }

    private fun setObservers() {
        viewModel.currentUserLiveData.handleResult {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }

            onSuccess {
                openNextPage(ForoomHomeContainerFragment(), false)
                splashScreen.setKeepOnScreenCondition { false }
            }

            onError {
                openNextPage(ForoomLoginFragment(), false)
                splashScreen.setKeepOnScreenCondition { false }
            }
        }
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
