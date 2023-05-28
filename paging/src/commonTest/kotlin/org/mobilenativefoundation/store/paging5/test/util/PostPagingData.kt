package org.mobilenativefoundation.store.paging5.test.util

import org.mobilenativefoundation.store.paging5.PagingData
import org.mobilenativefoundation.store.paging5.PagingParams

sealed class PostPagingData {
    data class Page(
        override val params: PagingParams<Int>,
        override val data: List<Post>,
        override val next: PagingParams<Int>?
    ) : PagingData.Page<Int, Post, Post>, PostPagingData()

    data class Item(
        override val id: Int,
        override val data: Post
    ) : PagingData.Item<Int, Post, Post>, PostPagingData()
}
