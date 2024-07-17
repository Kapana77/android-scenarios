package com.example.shared.extension

fun Int.isEven() = this % 2 == 0

const val INVALID_RESOURCE = -1
fun Int.ifValidResource(block: (Int)-> Unit) {
    if (!equals(INVALID_RESOURCE)) block(this)
}
