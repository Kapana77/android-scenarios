package com.example.shared.util.loading

interface GlobalLoadingDelegate {

    fun showGlobalLoading()

    fun hideGlobalLoading()

}

fun GlobalLoadingDelegate.isLoading(loading: Boolean) =
    if (loading) showGlobalLoading() else hideGlobalLoading()
