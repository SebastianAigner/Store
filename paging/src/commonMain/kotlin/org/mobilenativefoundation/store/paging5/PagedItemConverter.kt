package org.mobilenativefoundation.store.paging5

interface PagedItemConverter<InCollection : Any, AsSingle : Any> {
    suspend fun from(collection: InCollection): AsSingle
    suspend fun to(single: AsSingle): InCollection
}

