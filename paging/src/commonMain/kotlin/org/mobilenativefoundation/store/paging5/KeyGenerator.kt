package org.mobilenativefoundation.store.paging5

import org.mobilenativefoundation.store.cache5.Identifiable
import org.mobilenativefoundation.store.paging5.impl.DefaultKeyGenerator

interface KeyGenerator<Id : Any, InCollection : Identifiable<Id>, AsSingle : Identifiable<Id>> {
    fun fromSingle(value: AsSingle): PagingKey.Item<Id>

    companion object {
        fun <Id : Any, InCollection : Identifiable<Id>, AsSingle : Identifiable<Id>> default():
                KeyGenerator<Id, InCollection, AsSingle> = DefaultKeyGenerator<Id, InCollection, AsSingle>()
    }
}


