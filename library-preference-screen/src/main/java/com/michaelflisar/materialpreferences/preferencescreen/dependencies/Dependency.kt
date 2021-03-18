package com.michaelflisar.materialpreferences.preferencescreen.dependencies

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface Dependency<T: Any> {
    val setting: StorageSetting<T>

    suspend fun isEnabled(): Boolean

    fun observe(scope: CoroutineScope, context: CoroutineContext = Dispatchers.Main, callback: ((value: Boolean) -> Unit)) = setting.flow
            .map { isEnabled() }
            .onEach {
                withContext(context) {
                    callback(it)
                }
            }
            .launchIn(scope)
}