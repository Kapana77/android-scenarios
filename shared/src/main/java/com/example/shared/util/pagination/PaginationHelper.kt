package com.example.shared.util.pagination

interface PaginationHelper<T> {
    var pageSize: Int

    fun getOffset(): Int

    fun addPage(items: List<T>)

    fun getItems(): List<T>

    fun clearItems()
}