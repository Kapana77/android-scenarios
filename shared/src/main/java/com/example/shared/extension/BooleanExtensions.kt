package com.example.shared.extension

fun Boolean.ifNot(block: ()-> Unit) {
    if (!this) block()
}