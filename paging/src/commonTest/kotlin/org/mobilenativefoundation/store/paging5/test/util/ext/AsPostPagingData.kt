package org.mobilenativefoundation.store.paging5.test.util.ext

import org.mobilenativefoundation.store.paging5.PagingData
import org.mobilenativefoundation.store.paging5.PagingParams
import org.mobilenativefoundation.store.paging5.test.util.Post
import org.mobilenativefoundation.store.paging5.test.util.PostPagingData
import org.mobilenativefoundation.store.paging5.test.util.PostPagingParams

fun PagingData<Int, Post, Post>.asPostPagingData(): PostPagingData = when (this) {
    is PagingData.Item -> asPostPagingData()
    is PagingData.Page -> asPostPagingData()
}

fun PagingData.Item<Int, Post, Post>.asPostPagingData(): PostPagingData.Item =
    PostPagingData.Item(id, data)

fun PagingData.Page<Int, Post, Post>.asPostPagingData(): PostPagingData.Page =
    PostPagingData.Page(params, data, next)

fun PagingParams<Int>.asPostPagingParams() = PostPagingParams(loadSize, after, type)
