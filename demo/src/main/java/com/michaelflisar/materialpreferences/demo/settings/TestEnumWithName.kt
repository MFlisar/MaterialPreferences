package com.michaelflisar.materialpreferences.demo.settings

import com.michaelflisar.text.Text
import com.michaelflisar.text.asText

enum class TestEnumWithName(
        val label: Text
) {
    Red("Color Red".asText()),
    Green("Color Green".asText()),
    Blue("Color Blue".asText())
}