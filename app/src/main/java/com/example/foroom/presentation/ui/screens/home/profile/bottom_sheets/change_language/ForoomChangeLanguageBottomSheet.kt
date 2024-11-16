package com.example.foroom.presentation.ui.screens.home.profile.bottom_sheets.change_language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.design_system.components.bottom_sheet.ForoomActionBottomSheetFragment
import com.example.design_system.R
import com.alternator.foroom.databinding.LayoutChangeLanguageBottomSheetBinding
import com.example.shared.extension.onClick
import com.example.shared.model.ForoomLanguage
import com.example.shared.util.language_change.LanguageChangePoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForoomChangeLanguageBottomSheet :
    ForoomActionBottomSheetFragment<ForoomChangeLanguageViewModel, LayoutChangeLanguageBottomSheetBinding>() {

    override val inflateContent: (LayoutInflater, ViewGroup?, Boolean) -> LayoutChangeLanguageBottomSheetBinding =
        LayoutChangeLanguageBottomSheetBinding::inflate
    override val viewModel: ForoomChangeLanguageViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setListeners()
    }

    private fun initViews() {
        isButtonVisible = false

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                val selectedColor = requireContext().getColor(R.color.foroom_gray_dark)

                if (appLanguageDelegate?.getAppLanguage() == ForoomLanguage.EN) {
                    binding.languageButtonEng.background.setTint(selectedColor)
                } else {
                    binding.languageButtonGeo.background.setTint(selectedColor)
                }

                cancel()
            }
        }
    }

    private fun setListeners() {
        binding.languageButtonGeo.onClick {
            dismiss()
            appLanguageDelegate?.changeAppLanguage(ForoomLanguage.KA, LanguageChangePoint.POFILE)
        }

        binding.languageButtonEng.onClick {
            dismiss()
            appLanguageDelegate?.changeAppLanguage(ForoomLanguage.EN, LanguageChangePoint.POFILE)
        }
    }
}