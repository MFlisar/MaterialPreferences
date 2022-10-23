package com.michaelflisar.materialpreferences.preferencescreen.classes

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import com.michaelflisar.materialpreferences.preferencescreen.dpToPx
import com.michaelflisar.text.asText

sealed class Badge {

    class Text(val text: com.michaelflisar.text.Text, @ColorInt val tint: Int = Color.RED) : Badge()
    object Empty : Badge()

    fun display(tv: TextView) {
        when (this) {
            Empty -> {
                tv.text = ""
                tv.visibility = View.GONE
            }
            is Text -> {
                text.display(tv)
                tv.setTextColor(tint)
                (tv.background as GradientDrawable).setStroke(2.dpToPx, tint)
                tv.visibility = View.VISIBLE
            }
        }
    }
}

fun Int.asBatch(): Badge {
    return Badge.Text(this.asText())
}

fun String.asBatch(): Badge {
    return Badge.Text(this.asText())
}
