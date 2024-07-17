package com.example.shared.util.pagination

class PaginationHelperImpl<T>: PaginationHelper<T> {
    override var pageSize: Int = DEFAULT_PAGE_SIZE
    private val items = mutableListOf<T>()
    private var page = FIRST_PAGE

    override fun getItems(): List<T> = items

    override fun getOffset(): Int = pageSize * page

    override fun addPage(items: List<T>) {
        this.items.addAll(items)
        page++
    }

    override fun clearItems() {
        items.clear()
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 10
        private const val FIRST_PAGE = 0
    }
}