package com.example.shared.extension

fun Int.isEven() = this % 2 == 0

const val INVALID_VALUE = -1
fun Int.ifValidValue(block: (Int)-> Unit) {
    if (!equals(INVALID_VALUE)) block(this)
}

fun checkValue(value: Int, default: Int): Int {
    return if (value != INVALID_VALUE) value else default
}
