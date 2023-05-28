# Paging-Compose

Extensions for displaying paginated data with Jetpack Compose.

## Usage

```kotlin
@Composable
fun TimelineView(
    pager: Pager<Int, TimelineItem, TimelineItem>,
    onLoadMore: suspend () -> Unit
) {
    val lazyPagingItems = pager.collectAsLazyPagingItems(onLoadMore)

    LazyColumn {
        items(lazyPagingItems) { item ->
            when (item) {
                is TimelineItem.Post -> PostView(item)
                is TimelineItem.Upsell -> UpsellView(item)
                is TimelineItem.SuggestedContent -> SuggestedContentView(item)
            }
        }
    }
}
```