package com.dropbox.android.external.store4

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform

/**
 * A Store is responsible for managing a particular data request.
 *
 * When you create an implementation of a Store, you provide it with a Fetcher, a function that defines how data will be fetched over network.
 *
 * You can also define how your Store will cache data in-memory and on-disk. See [StoreBuilder] for full configuration
 *
 * Example usage:
 *
 * val store = StoreBuilder
 *  .fromNonFlow<Pair<String, RedditConfig>, List<Post>> { (query, config) ->
 *    provideRetrofit().fetchSubreddit(query, config.limit).data.children.map(::toPosts)
 *   }
 *  .persister(reader = { (query, _) -> db.postDao().loadPosts(query) },
 *             writer = { (query, _), posts -> db.postDao().insertPosts(query, posts) },
 *             delete = { (query, _) -> db.postDao().clearFeed(query) })
 *  .build()
 *  //single shot response
 *  viewModelScope.launch {
 *      val data = store.fresh(key)
 *  }
 *
 *  //get cached data and collect future emissions as well
 *  viewModelScope.launch {
 *    val data = store.cached(key, refresh=true)
 *                    .collect{data.value=it }
 *  }
 *
 */
interface Store<Key, Output> {

    /**
     * Return a flow for the given key
     * @param request - see [StoreRequest] for configurations
     */
    fun stream(request: StoreRequest<Key>): Flow<StoreResponse<Output>>

    /**
     * Purge a particular entry from memory and disk cache.
     * Persistant storage will only be cleared if a delete function was passed to
     * [StoreBuilder.persister] or [StoreBuilder.nonFlowingPersister] when creating the [Store].
     */
    suspend fun clear(key: Key)
}

@ExperimentalCoroutinesApi
@Deprecated("Legacy")
fun <Key, Output> Store<Key, Output>.stream(key: Key) = stream(
        StoreRequest.skipMemory(
                key = key,
                refresh = true
        )
).transform {
    it.throwIfError()
    it.dataOrNull()?.let { output ->
        emit(output)
    }
}

/**
 * Helper factory that will return data for [key] if it is cached otherwise will return fresh/network data (updating your caches)
 */
suspend fun <Key, Output> Store<Key, Output>.get(key: Key) = stream(
        StoreRequest.cached(key, refresh = false)
).filterNot {
    it is StoreResponse.Loading
}.first().requireData()

/**
 * Helper factory that will return fresh data for [key] while updating your caches
 */
suspend fun <Key, Output> Store<Key, Output>.fresh(key: Key) = stream(
        StoreRequest.fresh(key)
).filterNot {
    it is StoreResponse.Loading
}.first().requireData()
