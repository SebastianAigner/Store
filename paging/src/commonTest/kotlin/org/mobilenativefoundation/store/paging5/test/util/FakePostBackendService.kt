package org.mobilenativefoundation.store.paging5.test.util

class FakePostBackendService {
    fun getTimeline(
        params: PostPagingParams
    ): PostPagingData.Page {
        val start = params.after?.plus(1) ?: 1
        val end = start + params.loadSize
        val next = params.copy(after = end)

        return PostPagingData.Page(
            params = params,
            data = (start..end).map { id -> createPost(id) },
            next = next
        )
    }

    fun getPost(id: Int) = PostPagingData.Item(
        id = id,
        data = createPost(id)
    )

    private fun createPost(id: Int) = Post(id, "content_$id")
}
