package org.mobilenativefoundation.store.paging5.test

import app.cash.turbine.test
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.mobilenativefoundation.store.paging5.PagingParams
import org.mobilenativefoundation.store.paging5.test.util.PagingRepository
import org.mobilenativefoundation.store.paging5.test.util.PostPagingKey
import org.mobilenativefoundation.store.paging5.test.util.PostPagingParams
import org.mobilenativefoundation.store.paging5.test.util.PostPagingStoreBuilder
import org.mobilenativefoundation.store.paging5.test.util.ext.asPostPagingParams
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PagingTests {
    private val testScope = TestScope()
    @Test
    fun singleLoad() = testScope.runTest {
        val store = PostPagingStoreBuilder(this).build()
        val repository = PagingRepository(store)

        val initialPagingParams = PostPagingParams(
            loadSize = 20,
            after = null,
            type = PagingParams.Type.Append
        )

        repository.flow().test {
            repository.send(PostPagingKey.Page(initialPagingParams))

            val firstPagingState = awaitItem()
            assertEquals(21, firstPagingState.items.size)
            assertEquals(1, firstPagingState.pages.size)
            assertEquals(initialPagingParams.copy(after = 21), firstPagingState.next)
        }

    }

    @Test
    fun multipleLoads() = testScope.runTest {
        val store = PostPagingStoreBuilder(this).build()
        val repository = PagingRepository(store)

        val initialPagingParams = PostPagingParams(
            loadSize = 20,
            after = null,
            type = PagingParams.Type.Append
        )

        repository.flow().test {
            repository.send(PostPagingKey.Page(initialPagingParams))
            val firstPagingState = awaitItem()
            assertEquals(21, firstPagingState.items.size)
            assertEquals(1, firstPagingState.pages.size)

            val firstNext = firstPagingState.next
            assertNotNull(firstNext)
            assertEquals(initialPagingParams.copy(after = 21), firstNext)

            repository.send(PostPagingKey.Page(firstNext.asPostPagingParams()))

            val secondPagingState = awaitItem()

            assertEquals(42, secondPagingState.items.size)
            assertEquals(2, secondPagingState.pages.size)

            val secondNext = secondPagingState.next
            assertNotNull(secondNext)
            assertEquals(initialPagingParams.copy(after = 42), secondNext)

            repository.send(PostPagingKey.Page(secondNext.asPostPagingParams()))

            val thirdPagingState = awaitItem()
            assertEquals(63, thirdPagingState.items.size)
            assertEquals(3, thirdPagingState.pages.size)
        }
    }
}