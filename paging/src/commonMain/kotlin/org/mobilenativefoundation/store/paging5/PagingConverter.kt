package org.mobilenativefoundation.store.paging5

interface PagingConverter<Id : Any, InCollection : Any, AsSingle : Any> {
    val itemConverter: PagedItemConverter<InCollection, AsSingle>
    fun asPagingData(value: AsSingle): PagingData.Item<Id, InCollection, AsSingle>
}