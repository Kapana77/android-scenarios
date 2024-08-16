package com.example.shared.model

import com.example.shared.extension.EMPTY_STRING

data class Image(
    val id: Int,
    val url: String
) {

    companion object {
        fun getBlankImages(size: Int) = (0 until size).map {
            getBlankImage()
        }

        fun getBlankImage() = Image(BLANK_IMAGE_ID, EMPTY_STRING)

        const val BLANK_IMAGE_ID = -1
    }
}
