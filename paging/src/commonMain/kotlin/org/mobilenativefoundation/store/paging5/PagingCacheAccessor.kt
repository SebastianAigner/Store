package org.mobilenativefoundation.store.paging5

import org.mobilenativefoundation.store.cache5.CacheBuilder

class PagingCacheAccessor<Id : Any, InCollection : Any, AsSingle : Any> {
    private val pages = CacheBuilder<PagingKey.Page<Id>, PagingData.Page<Id, InCollection, AsSingle>>().build()
    private val items = CacheBuilder<PagingKey.Item<Id>, PagingData.Item<Id, InCollection, AsSingle>>().build()

    fun getPage(key: PagingKey.Page<Id>): PagingData.Page<Id, InCollection, AsSingle>? = pages.getIfPresent(key)
    fun getItem(key: PagingKey.Item<Id>): PagingData.Item<Id, InCollection, AsSingle>? = items.getIfPresent(key)

    fun putPage(key: PagingKey.Page<Id>, page: PagingData.Page<Id, InCollection, AsSingle>) {
        pages.put(key, page)
    }

    fun putItem(key: PagingKey.Item<Id>, item: PagingData.Item<Id, InCollection, AsSingle>) {
        items.put(key, item)
    }

    fun invalidateAll() {
        pages.invalidateAll()
        items.invalidateAll()
    }

    fun invalidateItem(key: PagingKey.Item<Id>) {
        items.invalidate(key)
    }

    fun invalidatePage(key: PagingKey.Page<Id>) {
        pages.invalidate(key)
    }
}
