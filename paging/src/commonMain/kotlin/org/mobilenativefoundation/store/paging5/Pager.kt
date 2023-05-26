package org.mobilenativefoundation.store.paging5

import kotlinx.coroutines.flow.StateFlow

typealias Pager<Id, InCollection, AsSingle> = StateFlow<PagingState<Id, InCollection, AsSingle>>
