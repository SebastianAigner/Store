package org.mobilenativefoundation.store.paging5.test.util

import org.mobilenativefoundation.store.paging5.PagingData
import org.mobilenativefoundation.store.paging5.PagingKey
import org.mobilenativefoundation.store.store5.Store

typealias PostPagingStore = Store<PagingKey<Int>, PagingData<Int, Post, Post>>