package org.mobilenativefoundation.store.paging5.test.util

import org.mobilenativefoundation.store.paging5.PagingKey

sealed class PostPagingKey {
    data class Page(
        override val params: PostPagingParams
    ) : PagingKey.Page<Int>, PostPagingKey()

    data class Item(
        override val id: Int
    ) : PagingKey.Item<Int>, PostPagingKey()
}