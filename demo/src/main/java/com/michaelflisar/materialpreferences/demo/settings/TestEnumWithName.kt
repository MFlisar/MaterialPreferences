package com.michaelflisar.materialpreferences.demo.settings

import com.michaelflisar.text.Text
import com.michaelflisar.text.toText

enum class TestEnumWithName(
        val label: Text
) {
    Red("Color Red".toText()),
    Green("Color Green".toText()),
    Blue("Color Blue".toText())
}