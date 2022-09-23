package com.michaelflisar.materialpreferences.core.settings

import com.michaelflisar.materialpreferences.core.interfaces.Storage
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

abstract class AbstractSetting<T : Any?> : StorageSetting<T> {

    abstract val customKey: String?
    protected abstract val storage: Storage
    protected abstract val cache: Boolean

    protected abstract fun createFlow(): Flow<T>
    protected abstract suspend fun persistValue(value: T)

    final override val flow: Flow<T> by lazy {
        createFlow().onEach(::updateCached)
    }

    final override suspend fun update(value: T) {
        persistValue(value)
        onValueChanged(value)
        // make cache update thread save by doing the update on the main thread
        if (storage.cache && cache) {
            withContext(Dispatchers.Main) {
                updateCached(value)
            }
        }
    }

    override val value: T
        get() {
            val c = cached
            return if (c != null)
                c.data
            else
                runBlocking(Dispatchers.IO) { flow.first() }
        }

    private suspend fun onValueChanged(value: T) {
        storage.onValueChanged(this, value)
    }

    // ---------
    // caching
    // ---------

    private var cached: Cached<T>? = null

    private fun updateCached(value: T) {
        if (storage.cache && cache) {
            cached = Cached(value)
        }
    }

    // ---------
    // classes
    // ---------

    private class Cached<T : Any?>(val data: T)
}