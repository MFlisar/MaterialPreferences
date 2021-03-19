package com.michaelflisar.materialpreferences.preferencescreen

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import androidx.annotation.ColorInt

internal fun Context.isUsingDarkTheme(): Boolean {
    val color = resolve(android.R.attr.colorBackground)
    val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
    return darkness > 0.5
}

internal val Context.colorAccent: Int
    @ColorInt get() = resolve(R.attr.colorAccent)

internal fun Context.resolve(attrId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrId, typedValue, true)
    return typedValue.data
}

internal val Int.dpToPx: Int get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()