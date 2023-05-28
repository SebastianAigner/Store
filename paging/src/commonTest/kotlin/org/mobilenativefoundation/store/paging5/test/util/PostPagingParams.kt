package org.mobilenativefoundation.store.paging5.test.util

import org.mobilenativefoundation.store.paging5.PagingParams

data class PostPagingParams(
    override val loadSize: Int,
    override val after: Int?,
    override val type: PagingParams.Type
) : PagingParams<Int>
