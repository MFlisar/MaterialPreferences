package com.michaelflisar.materialpreferences.preferencescreen.dependencies

import com.michaelflisar.kotpreferences.core.interfaces.StorageSetting

class BooleanDependency(
    override val setting: StorageSetting<Boolean>,
    private val dependOnTrue: Boolean = true
) : Dependency<Boolean> {

    override suspend fun state(): Boolean {
        return setting.read().let { if (dependOnTrue) it else !it }
    }
}

fun StorageSetting<Boolean>.asDependency(dependOnTrue: Boolean = true) =
    BooleanDependency(this, dependOnTrue)