package com.example.navigation.util

import androidx.fragment.app.Fragment
import com.example.navigation.host.ForoomNavigationHost

val Fragment.navigationHost
    get() = activity as? ForoomNavigationHost
