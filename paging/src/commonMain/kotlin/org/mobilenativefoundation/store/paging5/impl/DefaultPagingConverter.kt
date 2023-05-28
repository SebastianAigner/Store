package org.mobilenativefoundation.store.paging5.impl

import org.mobilenativefoundation.store.cache5.Identifiable
import org.mobilenativefoundation.store.paging5.PagingConverter
import org.mobilenativefoundation.store.paging5.PagingData

@Suppress("UNCHECKED_CAST")
class DefaultPagingConverter<Id : Any, InCollection : Identifiable<Id>, AsSingle : Identifiable<Id>> :
    PagingConverter<Id, InCollection, AsSingle> {
    override suspend fun from(collection: InCollection): AsSingle = collection as AsSingle
    override fun asPagingData(value: AsSingle): PagingData.Item<Id, InCollection, AsSingle> =
        object : PagingData.Item<Id, InCollection, AsSingle> {
            override val id: Id = value.id
            override val data: AsSingle = value
        }

}