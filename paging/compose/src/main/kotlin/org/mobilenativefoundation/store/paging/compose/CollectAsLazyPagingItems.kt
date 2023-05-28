package org.mobilenativefoundation.store.paging.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.mobilenativefoundation.store.paging5.Pager

@Composable
fun <Id : Any, AsSingle : Any> Pager<Id, *, AsSingle>.collectAsLazyPagingItems(onLoadMore: () -> Unit) =
    this.collectAsState().value.let { state -> LazyPagingItems(state.items, onLoadMore) }