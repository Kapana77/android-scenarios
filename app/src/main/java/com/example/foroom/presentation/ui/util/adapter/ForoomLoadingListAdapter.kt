package com.example.foroom.presentation.ui.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.foroom.databinding.LayoutForoomPaginationErrorBinding
import com.example.foroom.databinding.LayoutForoomPaginationLoadingBinding
import com.example.shared.util.diffutil.DefaultItemDiffCallback
import com.example.shared.util.recyclerview.RecyclerViewEndReachListener
import java.lang.ref.WeakReference

/**
 * ListAdapter with included loading and error states. Designed to be used with endless lists
 *
 * - TODO We need to replace viewType param with item object in [onCreateDataItemViewHolder]
 * - TODO We need to research if we can replace list filtration
 *
 * @param onErrorRefresh Callback which will be called when the user clicks on reload button on error state
 * */
@Suppress("UNCHECKED_CAST")
abstract class ForoomLoadingListAdapter<T : Any>(
    private val onLoadMore: () -> Unit = {},
    private val onErrorRefresh: () -> Unit = {},
    private val endReachType: RecyclerViewEndReachListener.Type = RecyclerViewEndReachListener.Type.BOTTOM
) : ListAdapter<ForoomLoadingListAdapter.LoadingListItemType,
        ForoomLoadingListAdapter.LoadingListDataViewHolder<T>>(DefaultItemDiffCallback()) {

    private var recyclerView: WeakReference<RecyclerView>? = null
    private var hasMorePages = false
    protected var isErrorState = false
        private set

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = WeakReference(recyclerView)
        recyclerView.addOnScrollListener(RecyclerViewEndReachListener(endReachType) {
            if (!isErrorState && currentList.size > EMPTY_LIST_SIZE) onLoadMore()
        })
    }

    final override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            LoadingListItemType.ErrorItem -> VIEW_TYPE_ERROR
            LoadingListItemType.LoadingItem -> VIEW_TYPE_LOADING
            else -> getDataItemViewType(item as LoadingListItemType.DataItem<T>, position)
        }
    }

    override fun onBindViewHolder(holder: LoadingListDataViewHolder<T>, position: Int) {
        val item = getItem(position)
        (item as? LoadingListItemType.DataItem<T>)?.let { holder.onBind(it.data, position) }
    }

    /**
     * Provide data item viewHolder implementation, error and loading viewHolders are created automatically
     *
     * @param parent ViewGroup to be used for view inflation
     * @param viewType Integer view type returned from [getDataItemViewType], defaults to -1
     * */
    abstract fun onCreateDataItemViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LoadingListDataViewHolder<T>

    /**
     * Provide viewType for your data item to implement multiple viewHolders according to the data types
     *
     * @param item Data item according which view type will be computed
     * @param position Data item position
     * */
    protected open fun getDataItemViewType(
        item: LoadingListItemType.DataItem<T>,
        position: Int
    ): Int {
        return VIEW_TYPE_DATA_DEFAULT
    }

    /**
     * Show error state at the end of the Recyclerview
     *
     * @param shouldClearOldList Boolean that determines if old items should be removed or not
     * */
    fun showErrorState(shouldClearOldList: Boolean = false) {
        // already an error state
        if (isErrorState) return

        isErrorState = true

        val list = if (shouldClearOldList) {
            emptyList()
        } else {
            currentList.filter { item ->
                item != LoadingListItemType.LoadingItem
            }
        }

        submitList(list + LoadingListItemType.ErrorItem) {
            recyclerView?.get()?.smoothScrollToPosition(currentList.lastIndex)
        }
    }

    /**
     * Submit list of your desired data, according which the corresponding list of [LoadingListItemType]
     * will be created.
     *
     * @param list Data list
     * @param hasMorePages Boolean to determine if the next page is expected, according which loading state will be configured
     **/
    fun submitDataList(list: List<T>, hasMorePages: Boolean) {
        this.hasMorePages = hasMorePages
        val typedList = mutableListOf<LoadingListItemType>()

        // When loading completely new list user should see the top of it
        val shouldScrollToTop = currentList.size == EMPTY_LIST_SIZE
                && currentList.firstOrNull() == LoadingListItemType.LoadingItem

        typedList.addAll(
            list.map { listItem ->
                LoadingListItemType.DataItem(listItem)
            }
        )

        if (hasMorePages && typedList.lastOrNull() != LoadingListItemType.LoadingItem) {
            typedList.add(LoadingListItemType.LoadingItem)
        }

        submitList(typedList) {
            if (shouldScrollToTop) recyclerView?.get()?.smoothScrollToPosition(FIRST_POSITION)
        }
    }

    /**
     * Clear recyclerview to prepare it for the new data
     * */
    fun clearData() {
        submitList(emptyList())
    }

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LoadingListDataViewHolder<T> {
        return when (viewType) {
            VIEW_TYPE_ERROR -> {
                val errorPageBinding = LayoutForoomPaginationErrorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                setReloadButtonClickListener(errorPageBinding)

                FooterViewHolder(
                    errorPageBinding
                )
            }

            VIEW_TYPE_LOADING -> {
                FooterViewHolder(
                    LayoutForoomPaginationLoadingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> onCreateDataItemViewHolder(parent, viewType)
        }
    }

    private fun setReloadButtonClickListener(binding: LayoutForoomPaginationErrorBinding) {
        binding.reloadButton.setOnClickListener {
            showLoadingState()
            onErrorRefresh()
        }
    }

    private fun showLoadingState() {
        isErrorState = false

        val loadingList = currentList.filter {
            it != LoadingListItemType.ErrorItem
        } + LoadingListItemType.LoadingItem

        submitList(loadingList)
    }

    /**
     * Base ViewHolder that needs to be extended in order adapter to function properly
     * */
    abstract class LoadingListDataViewHolder<T>(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Function to bind view with data
         * */
        abstract fun onBind(item: T, position: Int)
    }

    /**
     * ViewHolder for footer items which is managed by the API
     * */
    private class FooterViewHolder<T>(binding: ViewBinding) :
        LoadingListDataViewHolder<T>(binding) {
        override fun onBind(item: T, position: Int) {
            // no implementation needed
        }

    }

    sealed class LoadingListItemType {
        object LoadingItem : LoadingListItemType()
        object ErrorItem : LoadingListItemType()
        data class DataItem<T>(val data: T) : LoadingListItemType()
    }

    companion object {
        private const val VIEW_TYPE_LOADING = 100
        private const val VIEW_TYPE_ERROR = 101
        private const val VIEW_TYPE_DATA_DEFAULT = -1
        private const val EMPTY_LIST_SIZE = 1
        private const val FIRST_POSITION = 0
    }
}