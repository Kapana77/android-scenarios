package com.example.design_system.components.language_chooser

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.example.design_system.R
import com.example.design_system.databinding.LayoutLanguageImageViewBinding
import com.example.shared.extension.onClick
import com.example.shared.model.ForoomLanguage

class ForoomLanguageChooserView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    var languages = emptyList<ForoomLanguage>()
        private set

    var onLanguageSelected: (ForoomLanguage) -> Unit = {}
    var selectedIndex = 0
        private set

    init {
        background = ContextCompat.getDrawable(context, R.drawable.background_capsule)
        background.setTint(context.getColor(R.color.foroom_layer_background))

        resources.obtainAttributes(attrs, R.styleable.ForoomLanguageChooserView).apply {
            val names = getString(R.styleable.ForoomLanguageChooserView_languages)?.split(
                LANGUAGE_NAME_SPLIT_SYMBOL
            ).orEmpty()

            setLanguages(names.map { langName ->
                requireNotNull(ForoomLanguage.fromName(langName.trim())) {
                    "Language name incorrect, please use values from ForoomLanguageChooserView.Language"
                }
            })
        }.recycle()
    }

    fun setLanguages(languages: List<ForoomLanguage>) {
        this.languages = languages

        languages.forEachIndexed { index, language ->
            LayoutLanguageImageViewBinding.inflate(
                LayoutInflater.from(context), this, true
            ).root.apply {
                setImageDrawable(ContextCompat.getDrawable(context, getFlagForLanguage(language)))
                onClick {
                    if (index != selectedIndex) {
                        selectLanguage(index)
                        onLanguageSelected(language)
                    }
                }
            }
        }

        selectLanguage(selectedIndex)
    }

    fun selectLanguage(language: ForoomLanguage) {
        selectLanguage(languages.indexOf(language))
    }

    private fun selectLanguage(selectedIndex: Int) {
        children.forEachIndexed { index, languageImageview ->
            languageImageview.background.setTint(
                context.getColor(
                    if (selectedIndex == index) R.color.foroom_main_green
                    else android.R.color.transparent
                )
            )
        }
        this.selectedIndex = selectedIndex
    }

    private fun getFlagForLanguage(language: ForoomLanguage) = when (language) {
        ForoomLanguage.EN -> R.mipmap.ic_flag_uk
        ForoomLanguage.KA -> R.mipmap.ic_flag_geo
    }

    companion object {
        private const val LANGUAGE_NAME_SPLIT_SYMBOL = ","
    }
}