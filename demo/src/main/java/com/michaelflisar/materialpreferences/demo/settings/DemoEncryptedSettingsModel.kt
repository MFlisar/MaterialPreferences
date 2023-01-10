package com.michaelflisar.materialpreferences.demo.settings

import android.graphics.Color
import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.classes.SettingsGroup
import com.michaelflisar.materialpreferences.core.initialisation.SettingSetup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.datastore.DataStoreStorage
import com.michaelflisar.materialpreferences.demo.DemoStorageEncryption
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

object DemoEncryptedSettingsModel : SettingsModel(
    DataStoreStorage(
        name = "demo_encrypted_settings",
        cache = SettingSetup.ENABLE_CACHING, // false by default, only relevant for blocking reads
        encryption = DemoStorageEncryption
    )
) {

    // simple non nullable
    val int1 by intPref(1234)
    val bool1 by boolPref(false)
    val string1 by stringPref("Input 1")
    val long1 by longPref(100L)
    val float1 by floatPref(200.5f)
    val double1 by doublePref(300.5)

    // simple nullable
    val int2 by nullableIntPref(1234)
    val bool2 by nullableBoolPref(false)
    val string2 by nullableStringPref("Input 1")
    val long2 by nullableLongPref(100L)
    val float2 by nullableFloatPref(200.5f)
    val double2 by nullableDoublePref(300.5)

    // sets
    val stringSet1 by stringSetPref(setOf("a", "b", "c"))
    val intSet1 by intSetPref(setOf(1, 2, 3))
    val longSet1 by longSetPref(setOf(1L, 2L, 3L))
    val floatSet1 by floatSetPref(setOf(1f, 2f, 3f))
    val doubleSet1 by doubleSetPref(setOf(1.0, 2.0, 3.0))

}