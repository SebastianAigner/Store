package org.mobilenativefoundation.store.paging5

interface KeyGenerator<Id : Any, InCollection : Any, AsSingle : Any> {
    fun fromSingle(value: AsSingle): PagingKey.Item<Id>
    fun fromCollection(value: InCollection): PagingKey.Page<Id>
}
