package org.mobilenativefoundation.store.paging5

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.cache5.Identifiable
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.impl.extensions.get

@Suppress("UNCHECKED_CAST")
class DefaultPagingConverter<Id : Any, InCollection : Identifiable<Id>, AsSingle : Identifiable<Id>> : PagingConverter<Id, InCollection, AsSingle> {
    override val itemConverter: PagedItemConverter<InCollection, AsSingle> = object : PagedItemConverter<InCollection, AsSingle> {
        override suspend fun from(collection: InCollection): AsSingle {
            return collection as AsSingle
        }

        override suspend fun to(single: AsSingle): InCollection {
            return single as InCollection
        }
    }

    override fun asPagingData(value: AsSingle): PagingData.Item<Id, InCollection, AsSingle> {
        return object : PagingData.Item<Id, InCollection, AsSingle> {
            override val id: Id = value.id
            override val data: AsSingle = value
        }
    }
}

class DefaultKeyGenerator<Id : Any, InCollection : Identifiable<Id>, AsSingle : Identifiable<Id>> : KeyGenerator<Id, InCollection, AsSingle> {
    override fun fromSingle(value: AsSingle): PagingKey.Item<Id> {
        return object : PagingKey.Item<Id> {
            override val id: Id = value.id
        }
    }

    override fun fromCollection(value: InCollection): PagingKey.Page<Id> {
        return object : PagingKey.Page<Id> {
            override val params: PagingParams<Id> = object : PagingParams<Id> {
                override val loadSize: Int = 20
                override val after: Id? = null
                override val type: PagingParams.Type = PagingParams.Type.Append
            }
        }
    }
}

abstract class PagingRepository<Id : Any, InCollection : Identifiable<Id>, AsSingle : Identifiable<Id>> private constructor(
    private val scope: CoroutineScope,
    fetcher: Fetcher<PagingKey<Id>, PagingData<Id, InCollection, AsSingle>>,
    sourceOfTruth: SourceOfTruth<PagingKey<Id>, PagingData<Id, InCollection, AsSingle>>,
    converter: PagingConverter<Id, InCollection, AsSingle> = DefaultPagingConverter(),
    keyGenerator: KeyGenerator<Id, InCollection, AsSingle> = DefaultKeyGenerator(),
) {

    private val store = StoreBuilder.from(
        fetcher = fetcher,
        sourceOfTruth = sourceOfTruth,
        memoryCache = PagingCache(
            scope = scope,
            converter = converter,
            keyGenerator = keyGenerator
        )
    ).build()

    val pager: Pager<Id, InCollection, AsSingle> = MutableStateFlow(PagingState.initial())

    fun start(requests: Flow<PagingKey.Page<Id>>) {
        scope.launch {
            requests.collect { request ->
                when (val data = store.get(request)) {
                    is PagingData.Item -> {
                        handleItem(data)
                    }

                    is PagingData.Page -> {
                        handlePage(data)
                    }
                }
            }
        }
    }

    abstract fun handleItem(item: PagingData.Item<Id, InCollection, AsSingle>)
    abstract fun handlePage(page: PagingData.Page<Id, InCollection, AsSingle>)
}
