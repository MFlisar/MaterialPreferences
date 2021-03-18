package com.michaelflisar.materialpreferences.preferencescreen

import android.content.Context
import android.graphics.Color
import android.util.TypedValue

internal fun Context.isUsingDarkTheme(): Boolean {
    // deduce theme from background color
    val typedValue = TypedValue()
    theme.resolveAttribute(android.R.attr.colorBackground, typedValue, true)
    val color = typedValue.data

    val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
    return darkness > 0.5
}