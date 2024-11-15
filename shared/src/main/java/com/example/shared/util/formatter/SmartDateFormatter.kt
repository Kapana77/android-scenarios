package com.example.shared.util.formatter

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("ConstantLocale")
object SmartDateFormatter {
    private const val TIME_FORMATTER_PATTERN = "hh:mm"
    private const val DATE_TIME_FORMATTER_PATTERN = "d MMM, hh:mm"

    private val timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMATTER_PATTERN)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_PATTERN)

    fun formatDate(date: LocalDateTime?, formatParams: FormatParams): String {
        val currentDay = LocalDateTime.now().dayOfMonth
        val messageDate = date ?: LocalDateTime.now()

        return when (currentDay) {
            messageDate.dayOfMonth ->
                "${formatParams.today}, " + timeFormatter.format(messageDate)

            messageDate.dayOfMonth - 1 ->
                "${formatParams.yesterday}, " + timeFormatter.format(messageDate)

            else -> dateTimeFormatter.format(messageDate)
        }
    }

    data class FormatParams(val today: String, val yesterday: String)
}