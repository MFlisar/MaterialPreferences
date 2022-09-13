package com.michaelflisar.materialpreferences.preferencescreen.dependencies

import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting

class BooleanDependency(
    override val setting: StorageSetting<Boolean>
) : Dependency<Boolean> {

    override suspend fun state(): Boolean {
        return setting.read()
    }
}

fun StorageSetting<Boolean>.asDependency() = BooleanDependency(this)