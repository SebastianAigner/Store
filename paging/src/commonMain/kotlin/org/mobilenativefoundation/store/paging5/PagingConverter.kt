package org.mobilenativefoundation.store.paging5

import org.mobilenativefoundation.store.cache5.Identifiable
import org.mobilenativefoundation.store.paging5.impl.DefaultPagingConverter


interface PagingConverter<Id : Any, InCollection : Identifiable<Id>, AsSingle : Identifiable<Id>> {
    suspend fun from(collection: InCollection): AsSingle
    fun asPagingData(value: AsSingle): PagingData.Item<Id, InCollection, AsSingle>

    companion object {
        fun <Id : Any, InCollection : Identifiable<Id>, AsSingle : Identifiable<Id>> default():
                PagingConverter<Id, InCollection, AsSingle> = DefaultPagingConverter<Id, InCollection, AsSingle>()
    }
}