package com.example.shared.util.formatter

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

// Do not give a fuck about changing locale in runtime
@SuppressLint("ConstantLocale")
object SmartDateFormatter {
    private const val TIME_FORMATTER_PATTERN = "hh:mm"
    private const val DATE_TIME_FORMATTER_PATTERN = "d MMM, hh:mm"

    private val timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMATTER_PATTERN)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_PATTERN)

    fun formatDate(date: LocalDateTime?): String {
        val currentDay = LocalDateTime.now().dayOfMonth
        val date = date ?: LocalDateTime.now()

        return when (currentDay) {
            date.dayOfMonth -> "Today, " + timeFormatter.format(date)
            date.dayOfMonth - 1 -> "Yesterday, " + timeFormatter.format(date)
            else -> dateTimeFormatter.format(date)
        }
    }
}