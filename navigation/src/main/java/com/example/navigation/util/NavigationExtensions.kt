package com.example.navigation.util

import androidx.fragment.app.Fragment
import com.example.navigation.host.ForoomNavigationHost
import com.example.shared.ui.fragment.BaseFragment

val Fragment.navigationHost
    get() = parentFragment as? ForoomNavigationHost ?: activity as? ForoomNavigationHost
