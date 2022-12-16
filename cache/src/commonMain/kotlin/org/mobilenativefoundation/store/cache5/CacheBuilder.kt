package org.mobilenativefoundation.store.cache5

import kotlin.time.Duration

class CacheBuilder<Key : Any, Value : Any> {
    internal var concurrencyLevel = 4
        private set
    internal val initialCapacity = 16
    internal var maximumSize = UNSET
        private set
    internal var maximumWeight = UNSET
        private set
    internal var expireAfterAccess: Duration = Duration.INFINITE
        private set
    internal var expireAfterWrite: Duration = Duration.INFINITE
        private set
    internal var weigher: Weigher<Key, Value>? = null
        private set
    internal var ticker: Ticker? = null
        private set

    fun concurrencyLevel(producer: () -> Int): CacheBuilder<Key, Value> = apply {
        concurrencyLevel = producer.invoke()
    }

    fun maximumSize(maximumSize: Long): CacheBuilder<Key, Value> = apply {
        if (maximumSize < 0) {
            throw IllegalArgumentException("Maximum size must be non-negative.")
        }
        this.maximumSize = maximumSize
    }

    fun expireAfterAccess(duration: Duration): CacheBuilder<Key, Value> = apply {
        if (duration.isNegative()) {
            throw IllegalArgumentException("Duration must be non-negative.")
        }
        expireAfterAccess = duration
    }

    fun expireAfterWrite(duration: Duration): CacheBuilder<Key, Value> = apply {
        if (duration.isNegative()) {
            throw IllegalArgumentException("Duration must be non-negative.")
        }
        expireAfterWrite = duration
    }

    fun ticker(ticker: Ticker): CacheBuilder<Key, Value> = apply {
        this.ticker = ticker
    }

    fun weigher(maximumWeight: Long, weigher: Weigher<Key, Value>): CacheBuilder<Key, Value> = apply {
        if (maximumWeight < 0) {
            throw IllegalArgumentException("Maximum weight must be non-negative.")
        }

        this.maximumWeight = maximumWeight
        this.weigher = weigher
    }

    fun build(): Cache<Key, Value> {
        if (maximumSize != -1L && weigher != null) {
            throw IllegalStateException("Maximum size cannot be combined with weigher.")
        }
        return LocalCache.LocalManualCache(this)
    }

    companion object {
        private const val UNSET = -1L
    }
}