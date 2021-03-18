package com.michaelflisar.materialpreferences.preferencescreen.classes

import android.widget.ImageView

sealed class Icon {

    class Drawable(val drawable: android.graphics.drawable.Drawable) : Icon()
    class Resource(val res: Int) : Icon()
    object Empty : Icon()

    fun display(iv: ImageView) {
        when (this) {
            is Drawable -> iv.setImageDrawable(drawable)
            is Resource -> iv.setImageResource(res)
            is Empty -> iv.setImageDrawable(null)
        }
    }
}

fun Int.asIcon(): Icon {
    return Icon.Resource(this)
}

fun android.graphics.drawable.Drawable.asIcon(): Icon {
    return Icon.Drawable(this)
}