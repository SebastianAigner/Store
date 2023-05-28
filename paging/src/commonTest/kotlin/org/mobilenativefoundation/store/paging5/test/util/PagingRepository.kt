package org.mobilenativefoundation.store.paging5.test.util

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import org.mobilenativefoundation.store.paging5.Pager
import org.mobilenativefoundation.store.paging5.PagingConverter
import org.mobilenativefoundation.store.paging5.PagingData
import org.mobilenativefoundation.store.paging5.PagingKey
import org.mobilenativefoundation.store.paging5.PagingState
import org.mobilenativefoundation.store.store5.impl.extensions.get

class PagingRepository(
    private val store: PostPagingStore,
    private val converter: PagingConverter<Int, Post, Post> = PagingConverter.default()
) {

    private val requests =
        MutableSharedFlow<PagingKey.Page<Int>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val pagingStateFlow =
        MutableStateFlow<PagingState<Int, Post, Post>>(PagingState.initial())
    private val pager: Pager<Int, Post, Post> = pagingStateFlow

    suspend fun send(request: PagingKey.Page<Int>) {
        requests.emit(request)
    }

    fun flow() = requests.mapNotNull { request ->
        when (val data = store.get(request)) {
            is PagingData.Item -> {
                null
            }

            is PagingData.Page -> {
                val currentPagingState = pager.value
                val nextPages = currentPagingState.pages.toMutableList().apply { add(data) }
                val newItems = data.data.map { converter.asPagingData(it) }
                val nextItems =
                    currentPagingState.items.toMutableList().apply { addAll(newItems) }
                val nextPagingState = PagingState(
                    items = nextItems,
                    pages = nextPages,
                    next = data.next
                )
                pagingStateFlow.value = nextPagingState
                nextPagingState
            }
        }
    }
}