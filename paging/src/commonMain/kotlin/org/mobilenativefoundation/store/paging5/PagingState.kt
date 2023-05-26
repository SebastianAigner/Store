package org.mobilenativefoundation.store.paging5

data class PagingState<Id : Any, InCollection : Any, AsSingle : Any>(
    val pages: List<PagingData.Page<Id, InCollection, AsSingle>>,
    val items: List<PagingData.Item<Id, InCollection, AsSingle>>,
    val next: PagingParams<Id>? = null
) {
    companion object {
        fun <Id : Any, InCollection : Any, AsSingle : Any> initial() = PagingState<Id, InCollection, AsSingle>(
            pages = listOf(),
            items = listOf()
        )
    }
}
