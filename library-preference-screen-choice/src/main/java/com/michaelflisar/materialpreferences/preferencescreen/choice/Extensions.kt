package com.michaelflisar.materialpreferences.preferencescreen.choice

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.michaelflisar.text.Text

internal val Context.colorAccent: Int
    @ColorInt get() = resolve(R.attr.colorAccent)

internal val Context.textColor: ColorStateList?
    get() {
        val resolvedAttr = resolveThemeAttr(android.R.attr.textColorSecondary)
        val colorRes = if (resolvedAttr.resourceId != 0) resolvedAttr.resourceId else resolvedAttr.data
        return ContextCompat.getColorStateList(this, colorRes)
    }

internal fun Context.resolveThemeAttr(@AttrRes attrRes: Int): TypedValue {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue
}

internal fun Context.resolve(attrId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrId, typedValue, true)
    return typedValue.data
}