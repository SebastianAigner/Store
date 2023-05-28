package org.mobilenativefoundation.store.paging5.test.util

import org.mobilenativefoundation.store.cache5.Identifiable

data class Post(
    override val id: Int,
    val content: String
): Identifiable<Int>