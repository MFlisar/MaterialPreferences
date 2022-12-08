package com.michaelflisar.materialpreferences.preferencescreen.dependencies

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting

class BooleanDependency(
    override val setting: StorageSetting<Boolean>,
    val dependOnTrue: Boolean = true
) : Dependency<Boolean> {

    override suspend fun state(): Boolean {
        return setting.read().let { if (dependOnTrue) it else !it }
    }
}

fun StorageSetting<Boolean>.asDependency() = BooleanDependency(this)