package com.michaelflisar.materialpreferences.demo.settings

import com.michaelflisar.materialpreferences.core.SettingsConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class TestClass(
        val id: Int = 1,
        val name: String = ""
) {
    object CONVERTER : SettingsConverter<TestClass, String> {
        override fun from(data: String): TestClass = Json.decodeFromString(data)
        override fun to(data: TestClass): String = Json.encodeToString(data)
    }
}