package org.mobilenativefoundation.store.paging5.test.util.ext

import kotlinx.coroutines.flow.filter
import org.mobilenativefoundation.store.paging5.Pager

fun <Id : Any, InCollection : Any, AsSingle : Any> Pager<Id, InCollection, AsSingle>.pagingData() =
    filter { it.pages.isNotEmpty() && it.items.isNotEmpty() }