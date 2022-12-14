package org.mobilenativefoundation.store.store5.impl

import org.mobilenativefoundation.store.store5.NetworkFetcher
import org.mobilenativefoundation.store.store5.definition.GetRequest

internal class RealNetworkFetcher<Key : Any, Input : Any>(
    private val realGet: GetRequest<Key, Input>,
) : NetworkFetcher<Key, Input> {
    override suspend fun get(key: Key): Input = realGet(key)
}
