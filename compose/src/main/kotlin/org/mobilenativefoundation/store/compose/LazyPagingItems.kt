package org.mobilenativefoundation.store.compose

import org.mobilenativefoundation.store.paging5.PagingData

class LazyPagingItems<Id : Any, AsSingle : Any>(
    private val items: List<PagingData.Item<Id, *, AsSingle>>,
    private val onLoadMore: () -> Unit
) {
    val size: Int
        get() = items.size

    operator fun get(index: Int): AsSingle {
        if (index == size - 1) {
            onLoadMore()
        }
        return items[index].data
    }
}
