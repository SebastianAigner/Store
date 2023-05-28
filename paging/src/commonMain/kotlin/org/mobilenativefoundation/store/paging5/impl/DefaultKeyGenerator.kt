package org.mobilenativefoundation.store.paging5.impl

import org.mobilenativefoundation.store.cache5.Identifiable
import org.mobilenativefoundation.store.paging5.KeyGenerator
import org.mobilenativefoundation.store.paging5.PagingKey
import org.mobilenativefoundation.store.paging5.PagingParams

internal class DefaultKeyGenerator<Id : Any, InCollection : Identifiable<Id>, AsSingle : Identifiable<Id>> :
    KeyGenerator<Id, InCollection, AsSingle> {
    override fun fromSingle(value: AsSingle): PagingKey.Item<Id> = DefaultPagingKey.Item(value.id)
}

internal sealed class DefaultPagingKey<Id : Any> {
    data class Item<Id : Any>(
        override val id: Id
    ) : PagingKey.Item<Id>, DefaultPagingKey<Id>()

    data class Page<Id : Any>(
        override val params: PagingParams<Id>
    ) : PagingKey.Page<Id>, DefaultPagingKey<Id>()
}

internal data class DefaultPagingParams<Id : Any>(
    override val loadSize: Int,
    override val after: Id?,
    override val type: PagingParams.Type
) : PagingParams<Id>
