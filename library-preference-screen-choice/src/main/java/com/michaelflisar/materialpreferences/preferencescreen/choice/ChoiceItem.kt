package com.michaelflisar.materialpreferences.preferencescreen.choice

import com.michaelflisar.text.Text

interface ChoiceItem {
    val order: Int
    val label: Text
    val id: Int
}