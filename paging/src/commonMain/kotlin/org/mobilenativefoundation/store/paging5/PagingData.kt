package org.mobilenativefoundation.store.paging5

sealed interface PagingData<Id : Any, InCollection : Any, AsSingle : Any> {
    interface Page<Id : Any, InCollection : Any, AsSingle : Any> : PagingData<Id, InCollection, AsSingle> {
        val params: PagingParams<Id>
        val data: List<InCollection>
        val next: PagingParams<Id>?
    }

    interface Item<Id : Any, InCollection : Any, AsSingle : Any> : PagingData<Id, InCollection, AsSingle> {
        val id: Id
        val data: AsSingle
    }
}
